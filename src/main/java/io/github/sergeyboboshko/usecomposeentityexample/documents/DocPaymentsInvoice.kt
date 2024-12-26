package io.github.sergeyboboshko.usecomposeentityexample.documents

import android.app.LocaleManager
import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sergeyboboshko.composeentity.daemons.CommonDescribeField
import io.github.sergeyboboshko.composeentity.daemons.DocumentsButtonsSet
import io.github.sergeyboboshko.composeentity.daemons.FieldType
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.RenderFormFieldsReference
import io.github.sergeyboboshko.composeentity.daemons.RenderMainScreenList
import io.github.sergeyboboshko.composeentity.daemons.SuperTopDAO
import io.github.sergeyboboshko.composeentity.daemons._BaseDescribeFormElement
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons._SuperTopViewModel
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentEntity
import io.github.sergeyboboshko.composeentity.documents.base.CommonDocumentExtEntity
import io.github.sergeyboboshko.composeentity.documents.base.DocUI
import io.github.sergeyboboshko.composeentity.documents.base.TopDocumentRepository
import io.github.sergeyboboshko.composeentity.documents.base.TopDocumentViewModel
import io.github.sergeyboboshko.usecomposeentityexample.MyApplication1
import io.github.sergeyboboshko.usecomposeentityexample.daemons.___DocPayments
import kotlinx.coroutines.flow.Flow
import kotlinx.parcelize.Parcelize
import javax.inject.Inject
import kotlin.reflect.KClass
import io.github.sergeyboboshko.usecomposeentityexample.R
import io.github.sergeyboboshko.usecomposeentityexample.daemons.appGlobal

//******************** Entity --------------------------
@Parcelize
@Entity(tableName = "doc_payments_invoice")
data class DocPaymentsinvoiceEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    override var date: Long,
    override var number: Long,
    override var isPosted: Boolean,
    override var isMarkedForDeletion: Boolean,
    var describe: String?
) : CommonDocumentEntity(id, date, number, isPosted, isMarkedForDeletion), Parcelable


data class DocPaymentsinvoiceEntityExt(
    @Embedded override var link: DocPaymentsinvoiceEntity
) : CommonDocumentExtEntity<DocPaymentsinvoiceEntity>(link)

@Dao
interface DocPaymentsInvoiceDao :
    SuperTopDAO<DocPaymentsinvoiceEntity, DocPaymentsinvoiceEntityExt> {
    @RawQuery(observedEntities = [DocPaymentsinvoiceEntityExt::class])
    override fun queryExt(query: SupportSQLiteQuery): Flow<List<DocPaymentsinvoiceEntityExt>>

    @RawQuery(observedEntities = [DocPaymentsinvoiceEntity::class])
    override fun query(query: SupportSQLiteQuery): Flow<List<DocPaymentsinvoiceEntity>>
}

//******************    repository   **************************
//class DocPaymentsExpenseRepository(dao: DocPaymentsInvoiceDao) :
//    DocumentRepository<DocPaymentsExpenseEntity, DocPaymentsExpenseEntityExt, DocPaymentsInvoiceDao>(dao)
class DocPaymentsExpenseRepository @Inject constructor(
    dao: DocPaymentsInvoiceDao
) : TopDocumentRepository<DocPaymentsinvoiceEntity, DocPaymentsinvoiceEntityExt, DocPaymentsInvoiceDao>(
    dao
) {
    override val tableName: String = "doc_payments_invoice"
    override val myClass: KClass<DocPaymentsinvoiceEntity> = DocPaymentsinvoiceEntity::class
}

@HiltViewModel
class DocPaymentsInvoiceViewModel @Inject constructor(repository:DocPaymentsExpenseRepository) :
    TopDocumentViewModel<DocPaymentsinvoiceEntity, DocPaymentsinvoiceEntityExt, DocPaymentsInvoiceDao, DocPaymentsExpenseRepository>(
        repository
    ) {
    override var documentType = ___DocPayments
    init{
        showStandartFields()
        fieldDescriptions["date"]?.readOnly=true
        fieldDescriptions["describe"] = CommonDescribeField(
            fieldName = "describe",
            fieldType = FieldType.TEXT,
            label = MyApplication1.appContext.getString(R.string.description),
            placeholder = MyApplication1.appContext.getString(R.string.input_description)
        )
        fieldDescriptions["isMarkedForDeletion"] = CommonDescribeField(
            fieldName = "isMarkedForDeletion",
            fieldType = FieldType.BOOLEAN,
            label = LocalizationManager.getTranslation("markForDeletion"),
            placeholder = LocalizationManager.getTranslation("markForDeletion"),
            readOnly = false
        )
            IsInitialized=true
    }
}

//*******************   UI   **************************
class DocPaymentsInvoiceUI() : DocUI() {
//    //Accumulation registers filling
//    override var regs = listOf(
//        RegMoveElement("MyPayments",
//            "My Payments",
//            AccumRegMyPaymentsUI(GlobalContext.context),
//            documentType = DOCUMENT_TYPE_EXPENSE,
//            transactionType = TransactionType.EXPENSE,
//            emptyEntity = AccumRegMyPayments(0,System.currentTimeMillis(),0,0,0,TransactionType.EXPENSE,0,0,0.0))
//    )
//
//    override var infoRegs = listOf(
//        RegMoveElement("MyNotifications",
//            "My Notifications",
//            MyNotificationsUI(GlobalContext.context),
//            documentType = DOCUMENT_TYPE_EXPENSE,
//            transactionType = TransactionType.INFO,
//            emptyEntity = InfoRegMyNotifications(0,0,0,0,0,TransactionType.INFO,0,0.0,"")
//        )
//    )
    init {
        GlobalContext.mainViewModel?.anyUI = this
        accumMoves.registers = regs
        infoMoves.registers  = infoRegs
    }

    override var viewModel = appGlobal.docPaymentsInvoiceViewModel as _BaseFormVM
    //*************--------------------****************---------------**********************--------
    @Composable
    override fun MainScreenList() {

        GlobalContext.mainViewModel?.anyUI = this
        //NavigationTargets.current = "selfNav"
        RenderMainScreenList(viewModel as _SuperTopViewModel, "Expense Payments List", formList,this)

    }

    @Composable
    override fun AddEditScreen(isNew: Boolean, id: Long) {

        var caption = "The Payments Expence"
        RenderFormFieldsReference<DocPaymentsinvoiceEntity>(
            viewModel = viewModel as _SuperTopViewModel,
            isNew = isNew,
            caption1 = caption,
            buttonsSets = DocumentsButtonsSet(viewModel as _SuperTopViewModel, isNew,null,regs,infoRegs,haveTablePart = false),
            emptyEntity = DocPaymentsinvoiceEntity(
                0, System.currentTimeMillis(), 0L, false, false, ""
            )
        )
    }


    @Composable
    override fun ViewScreen(id: Long) {
        var caption = "Expense Payment Document"
        PerfectViewScreen(id = id,caption,true,false)

    }

    @Composable
    override fun ViewDetailsScreen(parentid: Long) {
//        val _viewModel: DocPaymentsExpenseDetailsListViewModel =
//            GlobalContext.docPaymentsExpenseDetailsListViewModel
//        //встановлюємо батьківські дані для проведення регистрів із дітейлс
//        _viewModel.setHatID(parentid, "From Payment Expence Details")
//        _viewModel.docEntity=viewModel.anyItem!!.link
//        _viewModel.docUI=this
//        //****************************************
//        val ui = DocPaymentstExpenseDetailsListUI(GlobalContext.context)
//        ui.MainScreenList()
    }

    override fun postMovements(docEntity: Any, rowEntities: List<Any>) {

    }

    @Composable
    override fun showMovements () {
        super.showMovements()
    }
}

//@Preview(name = "pw1")
//@Composable
//fun pv1 (){
//    DocPaymentsInvoiceUI().ViewScreen(1)
//}