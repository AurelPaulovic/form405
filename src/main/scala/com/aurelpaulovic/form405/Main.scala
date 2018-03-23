package com.aurelpaulovic.form405

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.aurelpaulovic.form405.config.{CliConfig, CliParser}
import com.aurelpaulovic.form405.data.CarRow
import com.aurelpaulovic.form405.xml.Form405
import kantan.csv._
import kantan.csv.generic._
import kantan.csv.ops._

import scala.xml.XML

object Main extends App {
  val cliParser = new CliParser

  cliParser.parse(args, CliConfig()) match {
    case Some(config) =>
      convert(config.input, config.output)
    case None =>
      println("Could not parse CLI config. Run again with `--help`.")
  }

  def convert(inputFile: File, outputFile: File): Unit = {
    implicit val localDateDecoder: CellDecoder[LocalDate] = {
      val format = DateTimeFormatter.ofPattern("d.M.yyyy")
      CellDecoder.from(s => DecodeResult(LocalDate.parse(s, format)))
    }

    val csvConf = CsvConfiguration.rfc.withHeader
    val parsedCars = inputFile.asCsvReader[CarRow](csvConf).toList

    if (parsedCars.exists(_.isLeft)) {
      // found a badly parsed row
      parsedCars.zipWithIndex.collect{ case (Left(error), idx) => (error, idx) }.foreach{
        case (error, rowIdx) => println(s"Found error when trying to read CSV file on row [${rowIdx + 2}]: $error")
      }
    } else {
      // all looks good
      val xml = Form405(parsedCars.collect{ case Right(carRow) => carRow })
      println(s"Saving data to XML file [${outputFile.getCanonicalPath}]")
      XML.save(outputFile.getCanonicalPath(), xml, "utf-8", true)
    }
  }
}


