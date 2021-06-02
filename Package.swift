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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/1.3.0/TaxKalculator.xcframework.zip",
            checksum: "777c3bfaad91019776f202a095e89efdf4cb065df924bb7947d733473b333b58"
        ),
    ]
)
