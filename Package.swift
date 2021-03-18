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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/1.0.1/TaxKalculator.xcframework.zip",
            checksum: "24be320fb8c8839d6e724caa9b0d599af34d7571cd00cfb449932d43f8f4eb97"
        ),
    ]
)
