package com.dmitrievanthony.duck.ledger;

import com.dmitrievanthony.duck.ledger.api.AccountRequestHandler;
import com.dmitrievanthony.duck.ledger.api.BalanceRequestHandler;
import com.dmitrievanthony.duck.ledger.api.DepositRequestHandler;
import com.dmitrievanthony.duck.ledger.api.TransferRequestHandler;
import com.dmitrievanthony.duck.ledger.api.WithdrawRequestHandler;
import com.dmitrievanthony.duck.ledger.api.exception.IllegalStateExceptionHandler;
import com.dmitrievanthony.duck.ledger.api.exception.InsufficientFundsExceptionHandler;
import com.dmitrievanthony.duck.ledger.core.InMemoryLedgerService;
import com.dmitrievanthony.duck.ledger.core.LedgerService;
import com.dmitrievanthony.duck.ledger.api.exception.IllegalArgumentExceptionHandler;
import com.dmitrievanthony.duck.ledger.core.exception.InsufficientFundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

import static spark.Spark.*;

/**
 * Main class of Duck Ledger application.
 */
public class Application {
    /** Logger. */
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    /** Web server port. */
    private final int port;

    /**
     * Constructs a new instance of application.
     *
     * @param port Web server port.
     */
    public Application(int port) {
        this.port = port;
    }

    /** Entry point. */
    public static void main(String... args) {
        new Application(8080).start();
    }

    /** Starts web server. */
    public void start() {
        initRestService(new InMemoryLedgerService());
    }

    /** Stops web server. */
    public void stop() throws InterruptedException {
        Spark.stop();

        // Spark doesn't provide such waiting functionality (see https://github.com/perwendel/spark/issues/705).
        Thread.sleep(1000);
    }

    /**
     * Initializes web server (including port, routes and exception handlers).
     *
     * @param ledgerService Ledger service to be used to process requests.
     */
    private void initRestService(LedgerService ledgerService) {
        port(port);

        exception(InsufficientFundsException.class, new InsufficientFundsExceptionHandler());
        exception(IllegalArgumentException.class, new IllegalArgumentExceptionHandler());
        exception(IllegalStateException.class, new IllegalStateExceptionHandler());

        post("/account", new AccountRequestHandler(ledgerService));
        post("/transfer", "application/json", new TransferRequestHandler(ledgerService));
        post("/withdraw/:id", "application/json", new WithdrawRequestHandler(ledgerService));
        post("/deposit/:id", "application/json", new DepositRequestHandler(ledgerService));

        get("/balance/:id", new BalanceRequestHandler(ledgerService));

        awaitInitialization();

        log.info("Application started [port=" + port + "]");
    }
}
