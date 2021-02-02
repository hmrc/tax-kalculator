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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/0.11.1/TaxKalculator.framework.zip",
            checksum: "a6fa980dde3ebd7eccb763804a8ee0cb0aeb6ee31641b5db0b745505cb3b6059"
        ),
    ]
)
