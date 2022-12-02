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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.7.1/TaxKalculator.xcframework.zip",
            checksum: "80aa18c07f97db63d89e975c3a78c4741b2e26ca995465b4082331a6884c031c"
        ),
    ]
)
