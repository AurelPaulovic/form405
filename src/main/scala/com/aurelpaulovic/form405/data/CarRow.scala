package com.aurelpaulovic.form405.data

import java.time.LocalDate

case class CarRow(
  ordNum: Int,
  sellerName: String,
  sellerAddress: String,
  countryCode: String,
  icDph: String,
  price: Int,
  vin: String,
  km: Int,
  tax: String,
  firstEvidence: LocalDate,
  invoiceCreation: LocalDate,
  invoiceEntry: LocalDate
)
