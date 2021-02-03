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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/0.12.0/TaxKalculator.xcframework.zip",
            checksum: "8dfb10f84f872250bd7ad1216b6b0670e42412e30443c08a10faeb15f458118b"
        ),
    ]
)
