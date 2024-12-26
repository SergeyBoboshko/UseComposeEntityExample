package io.github.sergeyboboshko.usecomposeentityexample.daemons

import io.github.sergeyboboshko.usecomposeentityexample.references.RefMeterZoneViewModel
import io.github.sergeyboboshko.usecomposeentityexample.documents.DocPaymentsInvoiceViewModel
import io.github.sergeyboboshko.usecomposeentityexample.references.RefAddressesViewModel
import io.github.sergeyboboshko.usecomposeentityexample.details.RefAddressDetailsViewModel
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersDetailsViewModel
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersViewModel
import io.github.sergeyboboshko.usecomposeentityexample.references.RefUtilitiesViewModel


object appGlobal {
    lateinit var refMeterZoneViewModel:RefMeterZoneViewModel
    lateinit var docPaymentsInvoiceViewModel:DocPaymentsInvoiceViewModel
    lateinit var refMetersViewModel:RefMetersViewModel
    lateinit var refMetersDetailsViewModel:RefMetersDetailsViewModel
    lateinit var refAddressesModel:RefAddressesViewModel
    lateinit var refUtilitiesViewModel:RefUtilitiesViewModel
    lateinit var refAddressDetailsViewModel:RefAddressDetailsViewModel
}