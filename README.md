
# tax-kalculator

[![Build Status](https://app.bitrise.io/app/cd7fb52c258b9273/status.svg?token=lntO8o4xz5AUEvLwVzbo3A&branch=master)](https://app.bitrise.io/app/cd7fb52c258b9273)
![LINE](https://img.shields.io/badge/line--coverage-98%25-brightgreen.svg)
![BRANCH](https://img.shields.io/badge/branch--coverage-94%25-brightgreen.svg)
![COMPLEXITY](https://img.shields.io/badge/complexity-1.48-brightgreen.svg)
[ ![Download](https://api.bintray.com/packages/hmrc/mobile-releases/tax-kalculator/images/download.svg) ](https://bintray.com/hmrc/mobile-releases/tax-kalculator/_latestVersion)

## Calculate take-home pay

First create an instance of `Calculator`, passing in the values as per the following example:

```kotlin
val calculator = Calculator(
    taxCode = "1250L",        // Required
    wages = 20000.0,          // Required
    payPeriod = YEARLY,       // Required
    isPensionAge = false,     // Optional (Default: false)
    hoursPerWeek = null,      // Optional (Default: null)
    taxYear = 2019            // Optional (Default: Current Tax Year)
)
```

> The default values are working in Android (and other JVM) but currently do not seem to be present in iOS, so pass in the default values for now.

Run calculations by calling `run()`:

```kotlin
val response = calculator.run()
```

This will returns an object of type `CalculatorResponse`. This class is broken up into weekly, four_weekly, monthly and yearly. Each of these members is of type `CalculatorResponsePayPeriod` and the members of this class are what will return the values (relative to their PayPeriod) needed for the app, they are:

- `taxToPay: Double`
- `employeesNI: Double`
- `employersNI: Double`
- `wages: Double`
- `taxBreakdown: List<BandBreakdown>`
- `taxFree: Double`
- `totalDeductions: Double`
- `takeHome: Double`

> For tax breakdown this is the amount of tax per tax band which has two members, `percentage: Double` and `amount: Double`.

## Validation

To validate a tax code:

```kotlin
val isValid = Calculator.isValidTaxCode("1250L") // true
```

To validate wages:

```kotlin
val isValidWages = Validator.isValidWages(1000) // true
val isAboveMinimumWages = Validator.isAboveMinimumWages(0.0) // false
val isBelowMaximumWages = Validator.isBelowMaximumWages(120000.0) // true
```

To validate hours worked per week:

```kotlin
val isValidHoursPerWeek = Validator.isValidHoursPerWeek(20) // true
val isAboveMinimumHoursPerWeek = Validator.isAboveMinimumHoursPerWeek(1.0) // true
val isBelowMaximumHoursPerWeek = Validator.isBelowMaximumHoursPerWeek(170.0) // false
```

## Development

To run unit tests and checks:

`./gradlew check`

To update the README badges:

`./gradlew cleanBuildTestCoverage`

## Download

### iOS

Add the framework to the project. All dependencies are bundled!

### Android or JVM

Add the mobile-releases bintray repository to your top-level `build.gradle`:

```groovy
repositories {
    maven {
        url  "https://hmrc.bintray.com/mobile-releases" 
    }
}
```

Add the dependency in the `build.gradle` of the module:

```groovy
dependencies {
    implementation "uk.gov.hmrc:tax-kalculator-jvm:x.y.z"
}
```

