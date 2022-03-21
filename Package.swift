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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.4.0/TaxKalculator.xcframework.zip",
            checksum: "6a36d545d8ebff996735ffcbafbe58a6556945ef5e3ff96c8d4f20bc876f4021"
        ),
    ]
)
