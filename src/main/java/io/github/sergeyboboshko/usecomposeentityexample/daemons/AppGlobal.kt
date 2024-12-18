package io.github.sergeyboboshko.usecomposeentityexample.daemons

import io.github.sergeyboboshko.usecomposeentityexample.references.RefMeterZoneViewModel
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsInvoiceViewModel
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersDetailsViewModel
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersViewModel


object appGlobal {
    lateinit var refMeterZoneViewModel:RefMeterZoneViewModel
    lateinit var docPaymentsInvoiceViewModel:DocPaymentsInvoiceViewModel
    lateinit var refMetersViewModel:RefMetersViewModel
    lateinit var refMetersDetailsViewModel:RefMetersDetailsViewModel
}