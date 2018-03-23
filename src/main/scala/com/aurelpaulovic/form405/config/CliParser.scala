package com.aurelpaulovic.form405.config

import java.io.File

class CliParser extends scopt.OptionParser[CliConfig]("form405") {
  head("form405", "0.1")

  opt[File]('i', "input")
    .required()
    .valueName("<inputFile>").text("Input CSV file containing data")
    .action( (file, conf) => conf.copy(input = file))

  opt[File]('o', "output")
    .required()
    .valueName("<outputFile>").text("Output XML file containing data")
    .action( (file, conf) => conf.copy(output = file))
}
