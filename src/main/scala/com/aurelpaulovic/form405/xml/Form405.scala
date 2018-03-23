package com.aurelpaulovic.form405.xml

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.aurelpaulovic.form405.data.CarRow

import scala.xml.{NodeSeq, Utility}

object Form405 {
  private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

  implicit class CarRowExt(val row: CarRow) {
    private val taxSpecial = "§66"

    def toXml = {
      <udaje>
        <poradCislo>{row.ordNum}</poradCislo>
        <predavajuci>
          <riadok>{Utility.escape(row.sellerName.trim)}</riadok>
          <riadok></riadok>
        </predavajuci>
        <sidloPredavajuci>
          <riadok>{Utility.escape(row.sellerAddress.trim)}</riadok>
          <riadok></riadok>
          <riadok></riadok>
        </sidloPredavajuci>
        <identifikacneCislo>
          <kodStatu>{Utility.escape(row.countryCode.trim)}</kodStatu>
          <cislo>{Utility.escape(row.icDph.trim)}</cislo>
        </identifikacneCislo>
        <hodnotaVozidla>{row.price}</hodnotaVozidla>
        <vin>{Utility.escape(row.vin.trim)}</vin>
        <pocetKm>{row.km}</pocetKm>
        <datumDoPrevadzky>{row.firstEvidence.format(dateFormatter)}</datumDoPrevadzky>
        <datumVyhotoveniaFa>{row.invoiceCreation.format(dateFormatter)}</datumVyhotoveniaFa>
        <datumNadobudnutia>{row.invoiceEntry.format(dateFormatter)}</datumNadobudnutia>
        <oslobodenieDane>{if (row.tax.trim != taxSpecial) 1 else 0 }</oslobodenieDane>
        <osobitnaUprava>{if (row.tax.trim == taxSpecial) 1 else 0 }</osobitnaUprava>
      </udaje>
    }
  }

  def headXml(carNum: Int) = {
    <hlavicka>
      <identifikacneCislo>
        <kodStatu>SK</kodStatu>
        <cislo></cislo>
      </identifikacneCislo>
      <typDP>
        <odp>0</odp>
        <ddp>0</ddp>
      </typDP>
      <zdanObd>
        <mesiac></mesiac>
        <stvrtrok></stvrtrok>
        <rok>2018</rok>
      </zdanObd>
      <pocetVozidiel>{carNum}</pocetVozidiel>
      <fyzickaOsoba>
        <priezvisko></priezvisko>
        <meno></meno>
        <titulPred></titulPred>
        <titulZa></titulZa>
      </fyzickaOsoba>
      <pravnickaOsoba>
        <obchodneMeno>
          <riadok></riadok>
          <riadok></riadok>
          <riadok></riadok>
        </obchodneMeno>
      </pravnickaOsoba>
      <sidlo>
        <ulica></ulica>
        <supisneOrientacneCislo></supisneOrientacneCislo>
        <psc></psc>
        <obec></obec>
        <stat></stat>
      </sidlo>
      <adresaSr>
        <ulica></ulica>
        <supisneOrientacneCislo></supisneOrientacneCislo>
        <psc></psc>
        <obec></obec>
      </adresaSr>
      <vypracoval>
        <vyhlasDna>{LocalDate.now().format(dateFormatter)}</vyhlasDna>
        <podpis>0</podpis>
      </vypracoval>
    </hlavicka>
  }

  def bodyXml(cars: Seq[CarRow]) = {
<telo>
{
  NodeSeq.fromSeq(cars.sliding(2, 2).collect{
    case Seq(car1) =>
      <priloha>
        {car1.toXml}
      </priloha>
    case Seq(car1, car2) =>
      <priloha>
        {car1.toXml}
        {car2.toXml}
      </priloha>
  }.toSeq)
}
</telo>
  }

  def apply(cars: Seq[CarRow]) = {
<dokument xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="zazmv2018.xsd">
  {headXml(cars.size)}
  {bodyXml(cars)}
</dokument>
  }
}
