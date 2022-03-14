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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.1.0/TaxKalculator.xcframework.zip",
            checksum: "1bb5eae8a2e133ab8b398bd926c23ec5f9aa9acc1504d2bdc51970ebcac1be39"
        ),
    ]
)
