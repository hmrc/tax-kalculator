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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.15.1/TaxKalculator.xcframework.zip",
            checksum: "8892fbc828b0ebc729fb7084a1c5e43ac62dc369e514df44cf162215354116bb"
        ),
    ]
)
