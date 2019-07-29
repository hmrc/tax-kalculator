
# tax-kalculator

##### Code Coverages
![LINE](https://img.shields.io/badge/line--coverage-97%25-brightgreen.svg)
![BRANCH](https://img.shields.io/badge/branch--coverage-93%25-brightgreen.svg)
![COMPLEXITY](https://img.shields.io/badge/complexity-1.37-brightgreen.svg)
##### Build Status

[![Build Status](https://app.bitrise.io/app/cd7fb52c258b9273/status.svg?token=lntO8o4xz5AUEvLwVzbo3A&branch=master)](https://app.bitrise.io/app/cd7fb52c258b9273)

## Build code locally

The gradle task to run before commit that runs a clean, build, test, coverage and updates the badges on the README is `./gradlew cleanBuildTestCoverage`

## Adding library to your project

### iOS

Just add the framework to the project all dependencies are bundled

### Android

Add `jcenter()` to `allProjects.repository` in the root `build.gradle`

In the app module `build.gradle` add the following line to your `dependencies`

```groovy
implementation "uk.gov.hmrc:tax-kalculator-jvm:0.0.4" //Update Version
```

### JVM

Add `jcenter()` to `repositories` in the root `build.gradle`

In the module `build.gradle` add the following line to your `dependencies`

```groovy
implementation "uk.gov.hmrc:tax-kalculator-jvm:0.0.4" //Update Version
```

## Using library

To use this library you need to call the `Calculator` contstructor and pass in the values as per the following example:

> The default values are working in Android (and other JVM) but currently do not seem to be present in iOS, so pass in the default values for now.
>
```kotlin
Calculator(
    taxCodeString = "1250L",        // Required
    userEnteredWages = 20000.0,     // Required
    payPeriod = PayPeriod.YEARLY,   // Required
    pensionAge = false,             // Optional (Default: false)
    hoursPerWeek = 0.0,             // Optional (Default: 0.0)
    taxYear = 2019                  // Optional (Default: Current Tax Year)
)
```

To run calculations and get the results be sure to call the `run()` function on the constructor.

This will returns an object of type `CalculatorResponse`. This class is broken up in to weekly, four_weekly, monthly amd yearly. Each of these members is of type `CalculatorResponsePayPeriod` and the members of this class are what will return the values (relative to their PayPeriod) needed for the app, they are:

- `taxToPay: Double`
- `employeesNI: Double`
- `employersNI: Double`
- `wages: Double`
- `taxBreakdown: List<BandBreakdown>`
- `taxFree: Double`
- `totalDeductions: Double`
- `takeHome: Double`

> For tax breakdown this is the amount of tax per tax band which has two members `percentage: Double` and `amount: Double`



