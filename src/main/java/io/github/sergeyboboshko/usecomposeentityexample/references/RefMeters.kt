package io.github.sergeyboboshko.usecomposeentityexample.references

import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.RawQuery
import androidx.room.Relation
import androidx.sqlite.db.SupportSQLiteQuery

import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.MyViewModel
import io.github.sergeyboboshko.composeentity.daemons.RenderFormFieldsReference
import io.github.sergeyboboshko.composeentity.daemons.RenderMainScreenList
import io.github.sergeyboboshko.composeentity.daemons.RenderViewScreen
import io.github.sergeyboboshko.composeentity.daemons.SuperTopDAO
import io.github.sergeyboboshko.composeentity.daemons.SuperTopRepository
import io.github.sergeyboboshko.composeentity.daemons._BaseDescribeFormElement
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons._SuperTopViewModel
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsEntity
import io.github.sergeyboboshko.composeentity.details.base.CommonDetailsExtEntity
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceExtEntity
import io.github.sergeyboboshko.composeentity.references.base.RefUI
import io.github.sergeyboboshko.composeentity.references.base.ReferenceIconButtonsSet
import io.github.sergeyboboshko.composeentity.references.base.TopReferenceViewModel


import io.github.sergeyboboshko.usecomposeentityexample.daemons.appGlobal
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.reflect.KClass

import io.github.sergeyboboshko.composeentity.details.base.DetailsRepository
import io.github.sergeyboboshko.composeentity.details.base.DetailsViewModel
import io.github.sergeyboboshko.composeentity.daemons.CommonDescribeSelectField
import io.github.sergeyboboshko.composeentity.daemons.FieldType
import io.github.sergeyboboshko.composeentity.daemons.BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons.DetailsButtonsSet
import io.github.sergeyboboshko.usecomposeentityexample.MyApplication1
import io.github.sergeyboboshko.usecomposeentityexample.R

//******************** Entity --------------------------
@Parcelize
@Entity(tableName = "ref_meters")
data class RefMetersEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    var addressId: Long
) : CommonReferenceEntity(id, date, name, isMarkedForDeletion), Parcelable {
    override fun toString(): String {
        return "$id: $name"
    }
}


data class RefMetersEntityExt(
    @Embedded override var link: RefMetersEntity,
    @Relation(
        parentColumn = "addressId", entityColumn = "id"
    ) var addressId: RefAddressesEntity?
) : CommonReferenceExtEntity<RefMetersEntity>(link)

@Dao
interface RefMetersDao : SuperTopDAO<RefMetersEntity, RefMetersEntityExt> {
    @RawQuery(observedEntities = [RefMetersEntityExt::class])
    override fun queryExt(query: SupportSQLiteQuery): Flow<List<RefMetersEntityExt>>

    @RawQuery(observedEntities = [RefMetersEntity::class])
    override fun query(query: SupportSQLiteQuery): Flow<List<RefMetersEntity>>
}

//******************      repository    **************************
class RefMetersRepository @Inject constructor(
    dao: RefMetersDao
) : SuperTopRepository<RefMetersEntity, RefMetersEntityExt, RefMetersDao>(dao) {
    override val myClass: KClass<RefMetersEntity> = RefMetersEntity::class
    override val tableName: String = "ref_meters"
}

//***************** view model factory  **************************
@HiltViewModel
class RefMetersViewModel @Inject constructor(
    repository: RefMetersRepository
) : TopReferenceViewModel<RefMetersEntity, RefMetersEntityExt, RefMetersDao, RefMetersRepository>(
    repository
) {
    // Мапа для опису полів
    override val fieldDescriptions = mutableMapOf<String, _BaseDescribeFormElement>()

    init {
        showStandartFields()
        //Others Fields
        val addressField =
            CommonDescribeSelectField<RefAddressesEntity, RefAddressesEntityExt, BaseFormVM<RefAddressesEntity, RefAddressesEntityExt>>(
                fieldName = "addressId",
                fieldType = FieldType.SELECT,
                label = MyApplication1.appContext.getString(R.string.address_label),
                placeholder = MyApplication1.appContext.getString(R.string.address_placeholder),
                emptyEntity = RefAddressesEntity(0, 0, "", false, "","","",0,"",0),
                viewModel = appGlobal.refAddressesModel
            )
        addressField.extName = "addressId"
        fieldDescriptions[addressField.fieldName] = addressField as _BaseDescribeFormElement
        //*************** Validators *****************
        //** name
        formValidator.addFieldValidator("name", object : FieldValidator {
            override val errorMessage = "Field Name mustn't be empty"
            override fun isValid(value: Any) = value.toString().isNotBlank()
        })
        IsInitialized = true
    }
}

//---UI
class RefMetersUI() :
    RefUI() {
    //override var form: Form = RefForm()
    //@OptIn(ExperimentalFoundationApi::class)
    override var viewModel = appGlobal.refMetersViewModel as _BaseFormVM

    @Composable
    override fun MainScreenList() {
        GlobalContext.mainViewModel?.anyUI = this
        //NavigationTargets.current = "selfNav"
        RenderMainScreenList(viewModel as _SuperTopViewModel, "Meters  List", formList, this)
    }

    @Composable
    override fun AddEditScreen(isNew: Boolean, id: Long) {

        //val viewModel: RefMeterZoneViewModel = GlobalContext.refMeterZoneViewModel
        var caption = "The Meters Reference"
        RenderFormFieldsReference(
            viewModel = viewModel as _SuperTopViewModel,
            isNew = isNew,
            caption1 = caption,
            ReferenceIconButtonsSet(viewModel as MyViewModel,haveTablePart = true),
            emptyEntity = RefMetersEntity(0, 0, "", false,0)
        )
    }

    @Composable
    override fun ViewScreen(id: Long) {
        PerfectViewScreen(id=id,
            caption = "Meter Zones",
            true)
//        Column {
//            RenderViewScreen(viewModel as MyViewModel, "The Metes Reference", formDetail)
//            val ref = viewModel.anyItem
//            val parentId = ref?.link?.id
//            ViewDetailsScreen (parentId?:0)
//        }
    }

    @Composable
    override fun ViewDetailsScreen(parentid: Long) {
        val _viewModel:RefMetersDetailsViewModel = appGlobal.refMetersDetailsViewModel
        _viewModel.setHatID(parentid,"")
        val ui = RefMeterDetailsUI()
        ui.MainScreenList()
    }

    override fun saveReferense() {
        TODO("Not yet implemented")
    }
}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//       DETAILS      DETAILS      DETAILS      DETAILS      DETAILS      DETAILS      DETAILS      DETAILS
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
@Entity(
    tableName = "ref_meters_details",
    foreignKeys = [
        ForeignKey(
            entity = RefMetersEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RefMetersDetailsEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    override var parentId: Long,
    var zonesId: Long
) : CommonDetailsEntity(id, parentId = parentId)

data class RefMetersDetailsExt(
    @Embedded override var link: RefMetersDetailsEntity,
    @Relation(
        parentColumn = "zonesId", entityColumn = "id"
    ) var zone: RefMeterZonesEntity?,
    @Relation(
        parentColumn = "parentId", entityColumn = "id"
    )
    var parent: RefMetersEntity?
) : CommonDetailsExtEntity<RefMetersDetailsEntity>(link)

//DAO
@Dao
interface RefMetersDetailsDao : SuperTopDAO<RefMetersDetailsEntity, RefMetersDetailsExt> {
    @RawQuery(observedEntities = [RefMetersDetailsExt::class])
    override fun queryExt(query: SupportSQLiteQuery): Flow<List<RefMetersDetailsExt>>

    @RawQuery(observedEntities = [RefMetersDetailsEntity::class])
    override fun query(query: SupportSQLiteQuery): Flow<List<RefMetersDetailsEntity>>
}

// REPOSITORY
//******************      repository    **************************
class RefMetersDetailsRepository @Inject constructor(
    dao: RefMetersDetailsDao
) : DetailsRepository<RefMetersDetailsEntity, RefMetersDetailsExt, RefMetersDetailsDao>(dao) {
    override val myClass: KClass<RefMetersDetailsEntity> = RefMetersDetailsEntity::class
    override var tableName: String = "ref_meters_details"
    override var parentId: Long = 0
}

// VIEW MODEL
@HiltViewModel
class RefMetersDetailsViewModel @Inject constructor(
    repository: RefMetersDetailsRepository
) : DetailsViewModel<RefMetersDetailsEntity, RefMetersDetailsExt, RefMetersDetailsDao, RefMetersDetailsRepository>(
    repository
) {
    // Мапа для опису полів
    override val fieldDescriptions = mutableMapOf<String, _BaseDescribeFormElement>()

    init {
        //zone
        val zoneField =
            CommonDescribeSelectField<RefMeterZonesEntity, RefMeterZonesEntityExt, BaseFormVM<RefMeterZonesEntity, RefMeterZonesEntityExt>>(
                fieldName = "zonesId",
                fieldType = FieldType.SELECT,
                label = "Meter Zone",
                placeholder = "Select Zone",
                emptyEntity = RefMeterZonesEntity(0, 0, "", false, 0),
                viewModel = appGlobal.refMeterZoneViewModel
            )
        zoneField.extName = "zone"
        fieldDescriptions[zoneField.fieldName] = zoneField as _BaseDescribeFormElement
        IsInitialized = true
    }
}

//*********************** UI  **************************
class RefMeterDetailsUI() :
    RefUI() {
    override var viewModel = appGlobal.refMetersDetailsViewModel as _BaseFormVM
    var parentID: Long = 0

    //@OptIn(ExperimentalFoundationApi::class)
    @Composable
    override fun MainScreenList() {
        val viewModel2 = viewModel as RefMetersDetailsViewModel
        parentID =viewModel2.getHatID()
        Log.d("PARENT_ID", "---RefMeterDetailsUI.MainScreenList() parentID = $parentID")
        GlobalContext.mainViewModel?.anyUI = this
        //NavigationTargets.current = "selfNav"
        RenderMainScreenList(viewModel, "Zones Meters  List",formList,this)

    }

    @Composable
    override fun AddEditScreen(isNew: Boolean, id: Long) {
        Log.d("PARENT_ID", "-- Details UI    fun AddEditScreen() parentId = $id, viewModel.parentId = ${(viewModel as DetailsViewModel<*, *, *, *>).getHatID()}")
        val dbs=DetailsButtonsSet(viewModel as MyViewModel,isNew)
        dbs.parentId = (viewModel as DetailsViewModel<*, *, *, *>).getHatID()
        var caption = "The Meter Zones Details element"
        RenderFormFieldsReference(
            viewModel = viewModel as _SuperTopViewModel,
            isNew = isNew,
            caption1 = caption,
            dbs,
            emptyEntity = RefMetersDetailsEntity(0,0,0)
        )
    }

    @Composable
    override fun ViewScreen(id: Long) {
        RenderViewScreen(viewModel, "The Meter Zones Details Element",formDetail)
    }

    @Composable
    override fun ViewDetailsScreen(parentid: Long) {
        TODO("Not yet implemented")
    }

    override fun saveReferense() {
        TODO("Not yet implemented")
    }

}



