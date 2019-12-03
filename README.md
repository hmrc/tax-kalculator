
# tax-kalculator

[![Build Status](https://app.bitrise.io/app/cd7fb52c258b9273/status.svg?token=lntO8o4xz5AUEvLwVzbo3A&branch=master)](https://app.bitrise.io/app/cd7fb52c258b9273)
![LINE](https://img.shields.io/badge/line--coverage-98%25-brightgreen.svg)
![BRANCH](https://img.shields.io/badge/branch--coverage-92%25-brightgreen.svg)
![COMPLEXITY](https://img.shields.io/badge/complexity-1.54-brightgreen.svg)
[ ![Download](https://api.bintray.com/packages/hmrc/mobile-releases/tax-kalculator/images/download.svg) ](https://bintray.com/hmrc/mobile-releases/tax-kalculator/_latestVersion)

## Calculate take-home pay

First create an instance of `Calculator`, passing in the values as per the following example:

```kotlin
val calculator = Calculator(
    taxCode = "1250L",     // Required
    wages = 20000.0,       // Required
    payPeriod = YEARLY,    // Required
    isPensionAge = false,  // Optional (Default: false)
    howManyAWeek = null,   // Optional (Default: null)
    taxYear = 2019         // Optional (Default: Current Tax Year)
)
```

> The default values are working in Android (and other JVM) but currently do not seem to be present in iOS, so pass in the default values for now.

Run calculations by calling `run()`:

```kotlin
val response = calculator.run()
```

This will returns an object of type `CalculatorResponse`. This class is broken up into weekly, four_weekly, monthly and yearly. Each of these members is of type `CalculatorResponsePayPeriod` and the members of this class are what will return the values (relative to their PayPeriod) needed for the app, they are:

- `taxToPay: Double` -> This will capped at a maximum of 50% of their wages
- `maxTaxAmountExceeded: Boolean` -> This will always be false unless taxToPay is adjusted for 50%
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
val isValid = TaxCodeValidator.isValidTaxCode("1250L") // TaxCodeValidationResponse(true)
val isValid = TaxCodeValidator.isValidTaxCode("OO9999") // TaxCodeValidationResponse(false, ValidationError.WrongTaxCodePrefix)
val isValid = TaxCodeValidator.isValidTaxCode("9999R") // TaxCodeValidationResponse(false, ValidationError.WrongTaxCodeSuffix)
val isValid = TaxCodeValidator.isValidTaxCode("HELLO") // TaxCodeValidationResponse(false, ValidationError.WrongTaxCodeNumber)
val isValid = TaxCodeValidator.isValidTaxCode("110") // TaxCodeValidationResponse(false, ValidationError.Other)

```

To validate wages:

```kotlin
val isValidWages = WageValidator.isValidWages(1000) // true
val isAboveMinimumWages = WageValidator.isAboveMinimumWages(0.0) // false
val isBelowMaximumWages = WageValidator.isBelowMaximumWages(120000.0) // true
```

To validate hours worked per week:

```kotlin
val isValidHoursPerWeek = HoursDaysValidator.isValidHoursPerWeek(20) // true
val isAboveMinimumHoursPerWeek = HoursDaysValidator.isAboveMinimumHoursPerWeek(1.0) // true
val isBelowMaximumHoursPerWeek = HoursDaysValidator.isBelowMaximumHoursPerWeek(170.0) // false
val isValidHoursPerDay = HoursDaysValidator.isValidHoursPerDay(20) // true
val isAboveMinimumHoursPerDay = HoursDaysValidator.isAboveMinimumHoursPerDay(1.0) // true
val isBelowMaximumHoursPerDay = HoursDaysValidator.isBelowMaximumHoursPerDay(25.0) // false
```

## Development

To run unit tests and checks:

`./gradlew check`

To update the README badges:

`./gradlew cleanBuildTestCoverage`

## Download

### iOS

Each release tag includes a Carthage binary dependency specification. To use the Carthage binary:
* In the same directory as your Cartfile, add a directory.
```shell script
 $ mkdir Carthage-Binaries
``` 
* Add a JSON file that holds your Carthage binary specifications.
```shell script
 $ touch Carthage-Binaries/TaxKalculator.json
```
* Point to the latest release in your JSON file.
```json
    {
      "0.3.7": "https://github.com/hmrc/tax-kalculator/releases/download/0.3.7/TaxKalculator.framework.zip"
    }
```
* List the dependency in your Cartfile
```shell script
    ...
    binary "Carthage-Binaries/TaxKalculator.json" == 0.3.7
    ...
```
* Update your Carthage dependencies as per your requirements.

#### Simulator Architectures: 
* Most Carthage users will include a Carthage [copy-frameworks](https://www.raywenderlich.com/416-carthage-tutorial-getting-started) build step that removes unwanted architectures for 
distribution builds.
* If you don't use Carthage and just download and link the framework in your project, it'll be necessary to strip unwanted architectures in a build step.
You may want to implement something like [this](http://ikennd.ac/blog/2015/02/stripping-unwanted-architectures-from-dynamic-libraries-in-xcode/).

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

