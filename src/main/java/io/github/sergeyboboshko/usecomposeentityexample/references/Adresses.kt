package io.github.sergeyboboshko.usecomposeentityexample.references

import android.os.Parcelable
import android.util.Log
import android.widget.Toast
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
import io.github.sergeyboboshko.composeentity.daemons.BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons.CommonDescribeField
import io.github.sergeyboboshko.composeentity.daemons.CommonDescribeSelectField
import io.github.sergeyboboshko.composeentity.daemons.DetailsButtonsSet
import io.github.sergeyboboshko.composeentity.daemons.FieldType
import io.github.sergeyboboshko.composeentity.daemons.FieldTypeHelper
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
import io.github.sergeyboboshko.composeentity.details.base.DetailsRepository
import io.github.sergeyboboshko.composeentity.details.base.DetailsViewModel
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceExtEntity
import io.github.sergeyboboshko.composeentity.references.base.RefUI
import io.github.sergeyboboshko.composeentity.references.base.ReferenceIconButtonsSet
import io.github.sergeyboboshko.composeentity.references.base.TopReferenceViewModel
import io.github.sergeyboboshko.composeentity_ksp.base.FormFieldCE
import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE
import io.github.sergeyboboshko.usecomposeentityexample.MyApplication1
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.reflect.KClass
import io.github.sergeyboboshko.usecomposeentityexample.R
import io.github.sergeyboboshko.usecomposeentityexample.daemons.appGlobal
import io.github.sergeyboboshko.usecomposeentityexample.details.RefAddressDetailsEntity
import io.github.sergeyboboshko.usecomposeentityexample.details.RefAddressDetailsUI
import io.github.sergeyboboshko.usecomposeentityexample.details.RefAddressDetailsViewModel

//******************** Entity --------------------------
@ObjectGeneratorCE(type = GeneratorType.Reference)
@Parcelize
@Entity(tableName = "ref_adresses")
data class RefAddressesEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    @FormFieldCE(render=true, label = "@@date_label", placeHolder = "@@date_placeholder",type = FieldTypeHelper.DATE_TIME)
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    @FormFieldCE(render=true, label = "Zipppp", type = FieldTypeHelper.TEXT)
    var zipCode: String,
    var city:String,
    var address:String,
    var houseNumber: Int,
    var houseBlock: String,
    var apartmentNumber: Int
) : CommonReferenceEntity(id, date, name, isMarkedForDeletion), Parcelable{
    override fun toString(): String {
        return "$id: $name"
    }
}


data class RefAddressesEntityExt(
    @Embedded override var link: RefAddressesEntity,
) : CommonReferenceExtEntity<RefAddressesEntity>(link)

//DAO
@Dao
interface RefAddressesDao : SuperTopDAO<RefAddressesEntity, RefAddressesEntityExt>{
    @RawQuery(observedEntities = [RefAddressesEntityExt::class])
    override fun queryExt(query: SupportSQLiteQuery): Flow<List<RefAddressesEntityExt>>

    @RawQuery(observedEntities = [RefAddressesEntity::class])
    override fun query(query: SupportSQLiteQuery): Flow<List<RefAddressesEntity>>
}

//******************      repository    **************************
class RefAddressesRepository @Inject constructor(
    dao: RefAddressesDao
) : SuperTopRepository<RefAddressesEntity, RefAddressesEntityExt, RefAddressesDao>(dao){
    override val myClass: KClass<RefAddressesEntity> = RefAddressesEntity::class
    override val tableName: String="ref_adresses"
}

//***************** view model factory  **************************
@HiltViewModel
class RefAddressesViewModel @Inject constructor(
    repository: RefAddressesRepository
) : TopReferenceViewModel<RefAddressesEntity, RefAddressesEntityExt, RefAddressesDao, RefAddressesRepository>(
    repository
) {
    // Мапа для опису полів
    override val fieldDescriptions = mutableMapOf<String, _BaseDescribeFormElement>()

    init {
        showStandartFields()
        //Standart Fields
        //** city
        fieldDescriptions["zipCode"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="zipCode",
                fieldType = FieldType.TEXT,
                label = MyApplication1.appContext.getString(R.string.zip_code_label),
                placeholder = MyApplication1.appContext.getString(R.string.zip_code_placeholder)
            ) as _BaseDescribeFormElement
        //** city
        fieldDescriptions["city"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="city",
                fieldType = FieldType.TEXT,
                label = MyApplication1.appContext.getString(R.string.city_label),
                placeholder = MyApplication1.appContext.getString(R.string.city_placeholder),
                onChange = {sity->
                    Log.d("ON_CHANGE_TEST","On change city = $sity")
                }
            ) as _BaseDescribeFormElement
        //** city
        fieldDescriptions["address"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="address",
                fieldType = FieldType.TEXT,
                label = MyApplication1.appContext.getString(R.string.address_label),
                placeholder = MyApplication1.appContext.getString(R.string.address_placeholder)
            ) as _BaseDescribeFormElement
        //** houseNumber
        fieldDescriptions["houseNumber"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="houseNumber",
                fieldType = FieldType.TEXT,
                label = MyApplication1.appContext.getString(R.string.houseNumber_label),
                placeholder = MyApplication1.appContext.getString(R.string.houseNumber_placeholder)
            ) as _BaseDescribeFormElement
        //** houseBlock
        fieldDescriptions["houseBlock"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="houseBlock",
                fieldType = FieldType.TEXT,
                label = MyApplication1.appContext.getString(R.string.houseBlock_label),
                placeholder = MyApplication1.appContext.getString(R.string.houseBlock_placeholder)
            ) as _BaseDescribeFormElement
        //** houseBlock
        fieldDescriptions["apartmentNumber"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="apartmentNumber",
                fieldType = FieldType.TEXT,
                label = MyApplication1.appContext.getString(R.string.apartmentNumber_label),
                placeholder = MyApplication1.appContext.getString(R.string.apartmentNumber_placeholder)
            ) as _BaseDescribeFormElement
        //*************** Validators *****************
        formValidator.addFieldValidator("name",object : FieldValidator {
            override val errorMessage = "Field Name mustn't be empty"
            override fun isValid(value: Any) = value.toString().isNotBlank()
        })
        //************************************************************

        IsInitialized=true
    }
}

//---UI
class RefAddressesUI() :
    RefUI() {
    override var viewModel = appGlobal.refAddressesModel as _BaseFormVM

    @Composable
    override fun MainScreenList() {
        GlobalContext.mainViewModel?.anyUI = this
        //NavigationTargets.current = "selfNav"
        RenderMainScreenList(viewModel as _SuperTopViewModel, "Adresses List",formList,this)
    }

    @Composable
    override fun AddEditScreen(isNew: Boolean, id: Long) {
        var caption = "The Adresses Reference"
        RenderFormFieldsReference(viewModel = viewModel as _SuperTopViewModel, isNew = isNew, caption1 = caption
            , ReferenceIconButtonsSet(viewModel as MyViewModel)
            ,emptyEntity = RefAddressesEntity(0,0,"",false,"00000","","",0,"",0))
    }

    @Composable
    override fun ViewScreen(id: Long) {
        PerfectViewScreen(id=id,
            caption = "Utilities",
            true,false)
    }

    @Composable
    override fun ViewDetailsScreen(parentid: Long) {
        val _viewModel:RefAddressDetailsViewModel = appGlobal.refAddressDetailsViewModel
        _viewModel.setHatID(parentid,"")
        val ui = RefAddressDetailsUI()
        ui.MainScreenList()
    }
@Composable
    override fun initMe() {
        TODO("Not yet implemented")
    }

    override fun saveReferense() {
        TODO("Not yet implemented")
    }

}