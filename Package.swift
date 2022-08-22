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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.6.1/TaxKalculator.xcframework.zip",
            checksum: "b746980e972e01a8d773e5ce91728f0846e65aa5a1e58edb7c9468aa467d61dc"
        ),
    ]
)
