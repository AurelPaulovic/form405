package com.aurelpaulovic.form405.config

import java.io.File

case class CliConfig (
  input: File = new File("."),
  output: File = new File(".")
)
