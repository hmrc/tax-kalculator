// swift-tools-version:5.3
import PackageDescription

let package = Package(
    name: "TaxKalculator",
    platforms: [
        .iOS(.v11)
    ],
    products: [
        .library(
            name: "TaxKalculator",
            targets: ["TaxKalculator"]
        ),
    ],
    targets: [
        .binaryTarget(
            name: "TaxKalculator",
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.5.0/TaxKalculator.xcframework.zip",
            checksum: "30c7f23440ed4716fe63b6727422f8cac05bfc99e924e18e6c86ad66484ec84d"
        ),
    ]
)
