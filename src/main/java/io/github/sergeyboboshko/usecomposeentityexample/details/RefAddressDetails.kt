package io.github.sergeyboboshko.usecomposeentityexample.details

import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.RawQuery
import androidx.room.Relation
import androidx.sqlite.db.SupportSQLiteQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sergeyboboshko.composeentity.daemons.BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons.CommonDescribeSelectField
import io.github.sergeyboboshko.composeentity.daemons.DetailsButtonsSet
import io.github.sergeyboboshko.composeentity.daemons.FieldType
import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.MyViewModel
import io.github.sergeyboboshko.composeentity.daemons.RenderFormFieldsReference
import io.github.sergeyboboshko.composeentity.daemons.RenderMainScreenList
import io.github.sergeyboboshko.composeentity.daemons.RenderViewScreen
import io.github.sergeyboboshko.composeentity.daemons.SuperTopDAO
import io.github.sergeyboboshko.composeentity.daemons._BaseDescribeFormElement
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons._SuperTopViewModel
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsExtEntity
import io.github.sergeyboboshko.composeentity.details.base.DetailsRepository
import io.github.sergeyboboshko.composeentity.details.base.DetailsViewModel
import io.github.sergeyboboshko.composeentity.references.base.RefUI
import io.github.sergeyboboshko.usecomposeentityexample.daemons.appGlobal
import io.github.sergeyboboshko.usecomposeentityexample.references.RefAddressesEntity
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersEntity
import io.github.sergeyboboshko.usecomposeentityexample.references.RefMetersEntityExt
import io.github.sergeyboboshko.usecomposeentityexample.references.RefUtilitiesEntity
import io.github.sergeyboboshko.usecomposeentityexample.references.RefUtilitiesEntityExt
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.reflect.KClass

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       DETAILS      DETAILS      DETAILS      DETAILS      DETAILS      DETAILS      DETAILS      DETAILS
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@Parcelize
@Entity(tableName = "ref_adress_details",
    foreignKeys = [
        ForeignKey(
            entity = RefAddressesEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RefAddressDetailsEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    override var parentId: Long,
    var utilityId: Long,
    var meterId: Long
) : CommonDetailsEntity(id, parentId = parentId), Parcelable

data class RefAddressDetailsExt(
    @Embedded override var link: RefAddressDetailsEntity,
    @Relation(
        parentColumn = "utilityId", entityColumn = "id"
    ) var utility: RefUtilitiesEntity?,
    @Relation(
        parentColumn = "meterId", entityColumn = "id"
    ) var meter: RefMetersEntity?,
    @Relation(
        parentColumn = "parentId", entityColumn = "id"
    )
    var parent: RefAddressesEntity?
) : CommonDetailsExtEntity<RefAddressDetailsEntity>(link)

//DAO
@Dao
interface RefAddressDetailsDao : SuperTopDAO<RefAddressDetailsEntity, RefAddressDetailsExt> {
    @RawQuery(observedEntities = [RefAddressDetailsExt::class])
    override fun queryExt(query: SupportSQLiteQuery): Flow<List<RefAddressDetailsExt>>

    @RawQuery(observedEntities = [RefAddressDetailsEntity::class])
    override fun query(query: SupportSQLiteQuery): Flow<List<RefAddressDetailsEntity>>
}

// REPOSITORY
//******************      repository    **************************
class RefAddressDetailsRepository @Inject constructor(
    dao: RefAddressDetailsDao
) : DetailsRepository<RefAddressDetailsEntity, RefAddressDetailsExt, RefAddressDetailsDao>(dao) {
    override val myClass: KClass<RefAddressDetailsEntity> = RefAddressDetailsEntity::class
    override var tableName: String = "ref_adress_details"
    override var parentId: Long = 0
}
// VIEW MODEL
@HiltViewModel
class RefAddressDetailsViewModel @Inject constructor(
    repository: RefAddressDetailsRepository
) : DetailsViewModel<RefAddressDetailsEntity, RefAddressDetailsExt, RefAddressDetailsDao, RefAddressDetailsRepository>(
    repository
) {
    // Мапа для опису полів
    override val fieldDescriptions = mutableMapOf<String, _BaseDescribeFormElement>()

    init {
        //utility
        val utilityField =
            CommonDescribeSelectField<RefUtilitiesEntity, RefUtilitiesEntityExt, BaseFormVM<RefUtilitiesEntity, RefUtilitiesEntityExt>>(
                fieldName = "utilityId",
                fieldType = FieldType.SELECT,
                label = "Utility",
                placeholder = "Select Utility",
                emptyEntity = RefUtilitiesEntity(0,0,"",false,"","",""),
                viewModel = appGlobal.refUtilitiesViewModel
            )
        utilityField.extName = "utility"
        fieldDescriptions[utilityField.fieldName] = utilityField as _BaseDescribeFormElement

        //utility
        val meterField =
            CommonDescribeSelectField<RefMetersEntity, RefMetersEntityExt, BaseFormVM<RefMetersEntity, RefMetersEntityExt>>(
                fieldName = "meterId",
                fieldType = FieldType.SELECT,
                label = "Meter",
                placeholder = "Select Meter",
                emptyEntity = RefMetersEntity(0,0,"",false,0),
                viewModel = appGlobal.refMetersViewModel
            )
        meterField.extName = "meter"
        fieldDescriptions[meterField.fieldName] = meterField as _BaseDescribeFormElement

        //*************** Validators *****************
        formValidator.addFieldValidator("utilityId",object : FieldValidator {
            override val errorMessage = "Field utility must be selected"
            override fun isValid(value: Any): Boolean {
                val curr = value as String
                val noErrors = curr.isNotEmpty() && !curr.equals("0")
                //Log.d("VALIDATE_MY_FORM","Users Validator utility ID noErrors= $noErrors")
                return noErrors
            }
        })

        IsInitialized = true
    }
}

//*********************** UI  **************************
class RefAddressDetailsUI() :
    RefUI() {
    override var viewModel = appGlobal.refAddressDetailsViewModel as _BaseFormVM
    var parentID: Long = 0

    //@OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun MainScreenList() {
        val viewModel2 = viewModel as RefAddressDetailsViewModel
        parentID =viewModel2.getHatID()
        GlobalContext.mainViewModel?.anyUI = this
        RenderMainScreenList(viewModel, "Utilities  List",formList,this)
    }

    @Composable
    override fun AddEditScreen(isNew: Boolean, id: Long) {
        val dbs=DetailsButtonsSet(viewModel as MyViewModel,isNew)
        dbs.parentId = (viewModel as DetailsViewModel<*, *, *, *>).getHatID()
        var caption = "The Adress Details element"
        RenderFormFieldsReference(
            viewModel = viewModel as _SuperTopViewModel,
            isNew = isNew,
            caption1 = caption,
            dbs,
            emptyEntity = RefAddressDetailsEntity(0,0,0,0)
        )
    }

    @Composable
    override fun ViewScreen(id: Long) {
        RenderViewScreen(viewModel, "The Adress Details Element",formDetail)
    }

    @Composable
    override fun ViewDetailsScreen(parentid: Long) {
        TODO("Not yet implemented")
    }

    override fun saveReferense() {
        TODO("Not yet implemented")
    }

}