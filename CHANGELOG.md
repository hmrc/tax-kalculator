# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/)
and this project adheres to [Semantic Versioning](http://semver.org/).

## [Unreleased]

## [2.11.2] - 2024-05-22Z
- Splitting student loan to student and postgraduate loan.
- Added new Clarification to describe student loan status.
- Added new "otherAmount" which combined student/postgrad/pension value.

## [2.11.1] - 2024-05-16Z
- Added `HAVE_NO_STATE_PENSION` Clarification.
- Added new tax code validator to validate tax code matching rate.
- Added new pension validator to validate Pension input.

## [2.11.0] - 2024-05-13Z
- Fix National Insurance calculation to use correct wage.
- Fix Student Loan internal calculation to use provided tax year instead of latest tax year only.
- Removed `standardLifetimeAllowance` for Pension validation.
- Allow Pension to go above `annualAllowance`.
- Fix Student Loan internal calculation to use yearly wage instead of deducting pension.
- Fix internal object so they're not accessible by jvm consumer.
- Refactor Pension Contribution to take one amount parameter instead of two.
- Added Clarification response list.
- Added more validation to `PensionValidator`. 
- Remove £9 from K and T tax code.
- Refactor Pension Contribution/Student Loan constructor
- Refactor Pension Contribution internal calculation to monthly instead of yearly.

## [2.10.2] - 2024-03-08Z

- Update Fastfile to create PR as part of release process.
- Update National Insurance Rate for 2024/25.

## [2.10.1] - 2024-03-07Z

- Updated the Kotlin version for compatibility with Xcode 15.

## [2.10.0] - 2024-02-23Z
- Added Pension Contribution calculation.
- Added Tapering calculation.
- Added Student Loan calculation.
- Added `userSuppliedTaxCode` to calculation, only calculate tapering if this value is `false`.
- Added 2024/25 rates.

## [2.9.3] - 2023-12-15Z
- Added 2024 January National Insurance rate.

## [2.9.2] - 2023-06-12Z
- Fixed bug to ignore additional characters on valid tax code.

## [2.9.1] - 2023-03-13Z
- Downgrade Kotlin version for compatibility.

## [2.9.0] - 2023-03-09Z
- Added 2023/24 rates.

## [2.8.0] - 2023-02-23Z
- Added support for Apple Silicon simulator.

## [2.7.1] - 2022-12-02Z
- Fixed issue with the `.jar` name for JVM consumers.

## [2.7.0] - 2022-11-24Z
- Updated November revised 2022 NI bands.

## [2.6.2] - 2022-08-23Z
- Fixed CI issues

## [2.6.1] - 2022-08-22Z
- Updated klock version to minimum version available on maven central

## [2.6.0] - 2022-05-27Z
- Updated July revised 2022 NI bands.

## [2.4.1] - 2022-03-22Z
- Restored `taxBreakdown` property to response.

## [2.4.0] - 2022-03-21Z
- Removed the internal concept of adjusted bands.
- Changed the way in which tax and NI is calculated and totalled.

## [2.3.0] - 2022-03-17Z
- Added adjustment for over £150k

## [2.2.0] - 2022-03-14Z
- Fixed access level issues and added a new helper for the default tax code.

## [2.1.0] - 2022-02-21Z
- Removed £9 adjustment from results.

## [2.0.0] - 2022-02-21
- Updated 2022/23 rates.

## [1.3.0] - 2021-06-02Z
- Removing `-jvm` from .jar name

## [1.2.0] - 2021-05-28
- Updated Scottish rates.

## [1.1.0] - 2021-04-07
- Changed to Github packages for artefact storage.

## [0.12.0] - 2021-02-03
- Added support for `Swift Package Manager`

## [0.11.1] - 2020-12-08
### Changed
- Added VCS Url
