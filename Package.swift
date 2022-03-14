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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.2.2/TaxKalculator.xcframework.zip",
            checksum: "4356bca5664878652ab2206bf03d6a3eeaf3bae45b547d4f7844dd1434d8fcee"
        ),
    ]
)
