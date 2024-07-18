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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.12.3/TaxKalculator.xcframework.zip",
            checksum: "f4344d36288fa01951a9a3e7f77ae01c7b27698012f014b0d95a654365d524e4"
        ),
    ]
)
