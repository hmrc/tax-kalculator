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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.0.0/TaxKalculator.xcframework.zip",
            checksum: "a3a1625f8c8bce3f9870fc23aa7d7cc09b13766881163bd5b7ffb849a9512fcf"
        ),
    ]
)
