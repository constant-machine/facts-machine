package com.factsmachine.service.error

class NoFactIdInRequestException: RuntimeException("Fact id should not be null")