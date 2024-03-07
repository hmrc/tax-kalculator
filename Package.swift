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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.10.1/TaxKalculator.xcframework.zip",
            checksum: "fee949bce987867caba360b9197ad45e1e33ddc957a446f4d1587e1d4a3a5dab"
        ),
    ]
)
