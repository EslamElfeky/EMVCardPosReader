package com.app.emvcard.domain.model

data class EmvStep(
    val index:Int,
    val name:String,
    val status: EmvStepStatus= EmvStepStatus.RUNNING,
    val detail: String?=null
)
