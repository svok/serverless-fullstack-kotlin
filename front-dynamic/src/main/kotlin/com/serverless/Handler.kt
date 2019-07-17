package com.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.apache.logging.log4j.LogManager
import sample.*

class Handler:RequestHandler<Map<String, Any>, ApiGatewayResponse> {
  override fun handleRequest(input: Map<String, Any>, context: Context):ApiGatewayResponse {
    LOG.info("received: " + input.keys.toString())

    val headers: Map<String, Any?> by input
//    val Accept:
//    val sample = Sample()
//    val platform = Platform
    val isObject = false

    return ApiGatewayResponse.build {
      statusCode = 200
      objectBody = if (isObject) HelloResponse(hello(), input) else null
      rawBody = if (!isObject) hello() else null
      this.headers = mapOf("X-Powered-By" to "AWS Lambda & serverless")
    }
  }

  companion object {
    private val LOG = LogManager.getLogger(Handler::class.java)
  }
}
