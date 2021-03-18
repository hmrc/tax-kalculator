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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/1.0.0/TaxKalculator.xcframework.zip",
            checksum: "7332dfaa42cd07a25a1902df305df8f03c2237598748669df9c92ac4a70a9eff"
        ),
    ]
)
