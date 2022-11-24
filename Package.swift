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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.7.0/TaxKalculator.xcframework.zip",
            checksum: "6c9f09d7963d77a79d15a76b4473141e78da0894756ea312632ae7d873d8b8ad"
        ),
    ]
)
