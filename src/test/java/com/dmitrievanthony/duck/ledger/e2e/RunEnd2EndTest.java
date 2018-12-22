package com.dmitrievanthony.duck.ledger.e2e;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = {"com.dmitrievanthony.duck.ledger.e2e"})
public class RunEnd2EndTest {}
