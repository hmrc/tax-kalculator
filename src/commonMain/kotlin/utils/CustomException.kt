package utils

class InvalidTaxCode(message: String) : Exception(message)

class UnsupportedTaxYear(message: String) : Exception(message)

class InvalidPayPeriod(message: String) : Exception(message)

class InvalidHours(message: String) : Exception(message)

class ConfigurationError(message: String) : Exception(message)