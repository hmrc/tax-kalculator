asdasdTemp change to be rolled back asas asddsdsdsdsdsfssdsdds
# tax-kalculator

[![Build Status](https://app.bitrise.io/app/cd7fb52c258b9273/status.svg?token=lntO8o4xz5AUEvLwVzbo3A&branch=master)](https://app.bitrise.io/app/cd7fb52c258b9273)
[![Github](https://img.shields.io/github/release/hmrc/tax-kalculator.svg)](https://gitHub.com/hmrc/tax-kalculator/releases/)
![swift-pm](https://img.shields.io/badge/SwiftPM-Compatible-success.svg)

# Contents
* [Calculate take-home pay](#calculate-take-home-pay)
* [Utilities](#utilities)
* [Validation](#validation)
* [Development](#development)
* [Usage](#usage)
* [Release process](#release-process)
* [How to update rates for new tax years](#how-to-update-rates-for–new-tax-years)
* [License](#license)

## Calculate take home pay

Create an instance of `Calculator`, providing values as per the following example:
#### Android
```kotlin
val calculator = Calculator(
    taxCode = "1257L",     // Required
    wages = 20000.0,       // Required
    payPeriod = YEARLY,    // Required
    isPensionAge = false,  // Optional (Default: false)
    howManyAWeek = null,   // Optional (Default: null)
    taxYear = 2022         // Optional (Default: Current Tax Year)
)

val response = calculator.run()
```
#### iOS
```swift
let calculator = Calculator(
    taxCode: "1257L",
    wages: 20000.0,
    payPeriod: period,
    isPensionAge: false,
    howManyAWeek: KotlinDouble(double: 35),
    taxYear: 2022
)

let calculation = try calculator.run()
```
> The default values are working in Android (and other JVM) but currently do not seem to be present in iOS, so pass in the default values for now.

Returns an object of type `CalculatorResponse`. This class is broken up into `weekly`, `four_weekly`, `monthly` and `yearly`. Each of these members is of type `CalculatorResponsePayPeriod` and the members of this class are what will return the values (relative to their PayPeriod) needed for the app, they are:

- `taxToPay` of type `Double` (This will be capped at a maximum of 50% of their wages)
- `maxTaxAmountExceeded` of type `Boolean` (This will always be false unless taxToPay is adjusted for 50%)
- `employeesNI` of type `Double`
- `employersNI` of type `Double`
- `wages` of type `Double`
- `taxBreakdown` of type `List<BandBreakdown>`
- `taxFree` of type `Double`
- `totalDeductions` of type `Double`
- `takeHome` of type `Double`

> For tax breakdown this is the amount of tax per tax band which has two members, `percentage: Double` and `amount: Double`.

## Utilities

### Default Tax Code
#### Android
```kotlin
val taxCode = CalculatorUtils.defaultTaxCode(taxYear = 2022)
```
#### iOS
```swift
let taxCode = CalculatorUtils.shared.defaultTaxCode(taxYear: 2022)
```

### Current Tax Year
#### Android
```kotlin
val year = CalculatorUtils.currentTaxYear()
```
#### iOS
```swift
let year = CalculatorUtils.shared.currentTaxYear()
```

## Validation

### Validate a tax code:
#### Android
```kotlin
val isValid = TaxCodeValidator.isValidTaxCode(taxCode = "1257L") // TaxCodeValidationResponse(true)
val isValid = TaxCodeValidator.isValidTaxCode(taxCode = "OO9999") // TaxCodeValidationResponse(false, ValidationError.WrongTaxCodePrefix)
val isValid = TaxCodeValidator.isValidTaxCode(taxCode = "9999R") // TaxCodeValidationResponse(false, ValidationError.WrongTaxCodeSuffix)
val isValid = TaxCodeValidator.isValidTaxCode(taxCode = "HELLO") // TaxCodeValidationResponse(false, ValidationError.WrongTaxCodeNumber)
val isValid = TaxCodeValidator.isValidTaxCode(taxCode = "110") // TaxCodeValidationResponse(false, ValidationError.Other)

```
#### iOS
```swift
let isValid = TaxCodeValidator.shared.isValidTaxCode(taxCode: "1257L") // TaxCodeValidationResponse(true)
let isValid = TaxCodeValidator.shared.isValidTaxCode(taxCode: "OO9999") // TaxCodeValidationResponse(false, ValidationError.WrongTaxCodePrefix)
let isValid = TaxCodeValidator.shared.isValidTaxCode(taxCode: "9999R") // TaxCodeValidationResponse(false, ValidationError.WrongTaxCodeSuffix)
let isValid = TaxCodeValidator.shared.isValidTaxCode(taxCode: "HELLO") // TaxCodeValidationResponse(false, ValidationError.WrongTaxCodeNumber)
let isValid = TaxCodeValidator.shared.isValidTaxCode(taxCode: "110") // TaxCodeValidationResponse(false, ValidationError.Other)

```
### Validate wages:
#### Android
```kotlin
val isValidWages = WageValidator.isValidWages(wages = 1000) // true
val isAboveMinimumWages = WageValidator.isAboveMinimumWages(wages = 0.0) // false
val isBelowMaximumWages = WageValidator.isBelowMaximumWages(wages = 120000.0) // true
```
#### iOS
```swift
let isValidWages = WageValidator.shared.isValidWages(wages: 1000) // true
let isAboveMinimumWages = WageValidator.shared.isAboveMinimumWages(wages: 0.0) // false
let isBelowMaximumWages = WageValidator.shared.isBelowMaximumWages(wages: 120000.0) // true
```

### Validate hours worked per week:
#### Android
```kotlin
val isValidHoursPerWeek = HoursDaysValidator.isValidHoursPerWeek(hours = 20) // true
val isAboveMinimumHoursPerWeek = HoursDaysValidator.isAboveMinimumHoursPerWeek(hours = 1.0) // true
val isBelowMaximumHoursPerWeek = HoursDaysValidator.isBelowMaximumHoursPerWeek(hours = 170.0) // false
val isValidHoursPerDay = HoursDaysValidator.isValidHoursPerDay(hours = 20) // true
val isAboveMinimumHoursPerDay = HoursDaysValidator.isAboveMinimumHoursPerDay(hours = 1.0) // true
val isBelowMaximumHoursPerDay = HoursDaysValidator.isBelowMaximumHoursPerDay(hours = 25.0) // false
```
#### Android
```swift
let isValidHoursPerWeek = HoursDaysValidator.shared.isValidHoursPerWeek(hours: 20) // true
let isAboveMinimumHoursPerWeek = HoursDaysValidator.shared.isAboveMinimumHoursPerWeek(hours: 1.0) // true
let isBelowMaximumHoursPerWeek = HoursDaysValidator.shared.isBelowMaximumHoursPerWeek(hours: 170.0) // false
let isValidHoursPerDay = HoursDaysValidator.shared.isValidHoursPerDay(hours: 20) // true
let isAboveMinimumHoursPerDay = HoursDaysValidator.shared.isAboveMinimumHoursPerDay(hours: 1.0) // true
let isBelowMaximumHoursPerDay = HoursDaysValidator.shared.isBelowMaximumHoursPerDay(hours: 25.0) // false
```

## Development

To run all tests and coverage verification:
```shell
./gradlew check
```

## Usage

### iOS
#### Swift Package Manager
> Because this operates as a closed source, binary dependency, Swift PM will only work with tagged releases and not branches.
```swift
https://github.com/hmrc/tax-kalculator
```

#### Simulator Architectures:
* If the framework is downloaded and linked in the project, it'll be necessary to strip unwanted architectures in a build step.
  * For example, you may want to implement something like [this](http://ikennd.ac/blog/2015/02/stripping-unwanted-architectures-from-dynamic-libraries-in-xcode/).

### Android & JVM

Add the Github Package repository to your top-level `build.gradle`, along with a Github username and access token (no permissions required).

```groovy
repositories {
    maven {
        url = "https://maven.pkg.github.com/hmrc/tax-kalculator"
        credentials {
            username = System.getenv("GITHUB_USER_NAME")
            password = System.getenv("GITHUB_TOKEN")
        }
    }
}
```

Add the dependency in the `build.gradle` of the module:

```groovy
dependencies {
    implementation "uk.gov.hmrc:tax-kalculator-jvm:x.y.z"
}
```

## Release process

```shell
bundle exec fastlane tag_release
```

### Required
* A valid Bitrise access token saved in your path under the variable name `BITRISE_TOKEN`. See [Bitrise docs](https://devcenter.bitrise.io/api/authentication).
* Two environment variables, `TAX_KALC_APP_SLUG` & `TAX_KALC_RELEASE_WORKFLOW_ID` will also need to be included in your bash/ZSH profile. Speak with [Chris](https://github.com/chrisob55) to obtain these values.

### Steps executed
* Ensure git status is clean
* Ensure `main` branch
* Through the interactive shell, select the tag version using semantic versioning.
* Locally executes `build_xcframework.sh`:
  * Creates an XCFramework
  * Computes and updates the checksum in the Swift Package declaration.
* Stamps the changelog
* Commit and push the updated `Package.swift` and `CHANGELOG.md`
* Upload release artifacts to tagged Github release
* Executes `release.sh` to start the CI pipeline on CI.

## How to update rates for new tax years

### Tax Bands
Each tax band is represented with the following data structure:

```kotlin
internal data class TaxBand(
    override var lower: Double, // lower threshold of band
    override var upper: Double, // upper threshold of band
    override val percentageAsDecimal: Double // tax rate of band
) : Band
```
In [`TaxBands.kt`](https://github.com/hmrc/tax-kalculator/blob/main/src/commonMain/kotlin/uk/gov/hmrc/calculator/model/bands/TaxBands.kt) update bands as specified by the business.
> Usually England and Wales have the same bands. Whereas Scotland's are different. 

```kotlin
// England and Wales
private fun restOfUK2022Bands() = listOf(
    TaxBand(0.0, 37700.00, 0.2),
    TaxBand(37700.00, 150000.00, 0.4),
    TaxBand(150000.0, -1.0, 0.45)
)

// Scotland
private fun scottish2022Bands() = listOf(
    TaxBand(0.00, 2162.00, 0.19),
    TaxBand(2162.00, 13118.00, 0.20),
    TaxBand(13118.00, 31092.00, 0.21),
    TaxBand(31092.00, 150000.00, 0.41),
    TaxBand(150000.00, -1.0, 0.46)
)
```

### National Insurance Bands
Employee and Employer bands are represented with the following data structures:

```kotlin
internal data class EmployeeNIBand(
    override var lower: Double, // lower threshold of band
    override var upper: Double, // upper threshold of band
    override val percentageAsDecimal: Double // tax rate of band
) : Band

internal data class EmployerNIBand(
    override var lower: Double, // lower threshold of band
    override var upper: Double, // upper threshold of band
    override val percentageAsDecimal: Double // tax rate of band
) : Band
```
In [`EmployeeNIBands.kt`](https://github.com/hmrc/tax-kalculator/blob/main/src/commonMain/kotlin/uk/gov/hmrc/calculator/model/bands/EmployeeNIBands.kt) 
and [`EmployerNIBands.kt`](https://github.com/hmrc/tax-kalculator/blob/main/src/commonMain/kotlin/uk/gov/hmrc/calculator/model/bands/EmployerNIBands.kt) update bands as specified by the business.

```kotlin
private val employeeNIBands2022: List<EmployeeNIBand> = listOf(
    EmployeeNIBand(9880.0, 50270.00, 0.1325),
    EmployeeNIBand(50270.0, -1.0, 0.0325)
)

private val employerNIBands2022: List<EmployerNIBand> = listOf(
    EmployerNIBand(9100.0, 50270.00, 0.1505),
    EmployerNIBand(50270.0, -1.0, 0.1505)
)
```

## License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
