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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.9.2/TaxKalculator.xcframework.zip",
            checksum: "7709b1086f3540f73eb66edf2ec763ec09a56d68de3b80d109e4238276af9f13"
        ),
    ]
)
