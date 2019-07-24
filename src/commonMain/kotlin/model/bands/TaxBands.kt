package model.bands

import model.Country
import model.Country.SCOTLAND
import model.Country.WALES

class TaxBands(country: Country, taxYear: Int) {

    //    2020
    private val wales2020: List<Band> = listOf(TaxBand(0.0, 20000.00, 0.1))
    private val scotland2020: List<Band> = listOf(TaxBand(0.0, 21000.00, 0.15))
    private val other2020: List<Band> = listOf(TaxBand(0.0, 22000.00, 0.20))

    //    2019
    private val scotland2019: List<Band> = listOf(
        TaxBand(0.0, 12509.00, 0.0),
        TaxBand(12509.00, 14549.00, 0.19),
        TaxBand(14549.00, 24944.00, 0.20),
        TaxBand(24944.00, 43430.00, 0.21),
        TaxBand(43430.00, 150000.00, 0.41),
        TaxBand(150000.0, 5000000.00, 0.46) //TODO Fix the 5million...
    )
    private val other2019: List<Band> = listOf(
        TaxBand(0.0, 12509.00, 0.0),
        TaxBand(12509.0, 50000.00, 0.2),
        TaxBand(50000.0, 150000.00, 0.4),
        TaxBand(150000.0, 5000000.00, 0.45)//TODO Fix the 5million...
    )

    val bands: List<Band> = when (taxYear) {
        2020 -> when (country) {
            WALES -> wales2020
            SCOTLAND -> scotland2020
            else -> other2020
        }
        2019 -> when (country) {
            SCOTLAND -> scotland2019
            else -> other2019
        }
        else -> throw IllegalArgumentException("Unsupported Year")
    }
}