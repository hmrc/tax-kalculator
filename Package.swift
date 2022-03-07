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
            url: "https://github.com/IntenseBits/tax-calculator/raw/main/TaxKalculator.xcframework.zip",
            checksum: "110f5d0fb145a8347d0acec4bf0b272540d9d78a7952c2b29f7e477cbe34a754"
        ),
    ]
)
