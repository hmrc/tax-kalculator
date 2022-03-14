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
            url: "https://github.com/hmrc/tax-kalculator/releases/download/2.2.1/TaxKalculator.xcframework.zip",
            checksum: "93d3dd0bbb6a68b72235dd19ffeffb4930f005d6e09c13461ee5046b910a7dbf"
        ),
    ]
)
