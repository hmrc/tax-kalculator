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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.12.4/TaxKalculator.xcframework.zip",
            checksum: "69e5e0a1044d097e4af43e191fd504473cd472abe16acfecba9ac14a24fa53f3"
        ),
    ]
)
