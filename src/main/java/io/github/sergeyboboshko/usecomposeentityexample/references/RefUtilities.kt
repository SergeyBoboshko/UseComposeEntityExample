package io.github.sergeyboboshko.usecomposeentityexample.references

import android.os.Parcelable
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sergeyboboshko.composeentity.daemons.CommonDescribeField
import io.github.sergeyboboshko.composeentity.daemons.FieldType
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
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceExtEntity
import io.github.sergeyboboshko.composeentity.references.base.RefUI
import io.github.sergeyboboshko.composeentity.references.base.ReferenceIconButtonsSet
import io.github.sergeyboboshko.composeentity.references.base.TopReferenceViewModel
import io.github.sergeyboboshko.usecomposeentityexample.MyApplication1
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.reflect.KClass
import io.github.sergeyboboshko.usecomposeentityexample.R
import io.github.sergeyboboshko.usecomposeentityexample.daemons.appGlobal

//******************** Entity --------------------------
@Parcelize
@Entity(tableName = "ref_utilities")
data class RefUtilitiesEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    var address:String,
    var serviceAccount:String,
    var describe:String
) : CommonReferenceEntity(id, date, name, isMarkedForDeletion), Parcelable{
    override fun toString(): String {
        return "$id: $name"
    }
}


data class RefUtilitiesEntityExt(
    @Embedded override var link: RefUtilitiesEntity,
) : CommonReferenceExtEntity<RefUtilitiesEntity>(link)

//DAO
@Dao
interface RefUtilitiesDao : SuperTopDAO<RefUtilitiesEntity, RefUtilitiesEntityExt>{
    @RawQuery(observedEntities = [RefUtilitiesEntityExt::class])
    override fun queryExt(query: SupportSQLiteQuery): Flow<List<RefUtilitiesEntityExt>>

    @RawQuery(observedEntities = [RefUtilitiesEntity::class])
    override fun query(query: SupportSQLiteQuery): Flow<List<RefUtilitiesEntity>>
}

//******************      repository    **************************
class RefUtilitiesRepository @Inject constructor(
    dao: RefUtilitiesDao
) : SuperTopRepository<RefUtilitiesEntity, RefUtilitiesEntityExt, RefUtilitiesDao>(dao){
    override val myClass: KClass<RefUtilitiesEntity> = RefUtilitiesEntity::class
    override val tableName: String="ref_utilities"
}

//***************** view model factory  **************************
@HiltViewModel
class RefUtilitiesViewModel @Inject constructor(
    repository: RefUtilitiesRepository
) : TopReferenceViewModel<RefUtilitiesEntity, RefUtilitiesEntityExt, RefUtilitiesDao, RefUtilitiesRepository>(
    repository
) {
    // Мапа для опису полів
    override val fieldDescriptions = mutableMapOf<String, _BaseDescribeFormElement>()

    init {
        showStandartFields()
        //Standart Fields
        // English Localization
        LocalizationManager.addLocalization("en", mapOf(
            "serviceAccountLabel" to "Service Account",
            "serviceAccountPlaceholder" to "Enter Service Account",
            "addressLabel" to "Address",
            "addressPlaceholder" to "Enter Address",
            "describeLabel" to "Description",
            "describePlaceholder" to "Enter Description"
        ))

// Hindi Localization
        LocalizationManager.addLocalization("hi", mapOf(
            "serviceAccountLabel" to "सेवा खाता",
            "serviceAccountPlaceholder" to "सेवा खाता दर्ज करें",
            "addressLabel" to "पता",
            "addressPlaceholder" to "पता दर्ज करें",
            "describeLabel" to "विवरण",
            "describePlaceholder" to "विवरण दर्ज करें"
        ))

// Spanish Localization
        LocalizationManager.addLocalization("es", mapOf(
            "serviceAccountLabel" to "Cuenta de Servicio",
            "serviceAccountPlaceholder" to "Ingrese Cuenta de Servicio",
            "addressLabel" to "Dirección",
            "addressPlaceholder" to "Ingrese Dirección",
            "describeLabel" to "Descripción",
            "describePlaceholder" to "Ingrese Descripción"
        ))

// Ukrainian Localization
        LocalizationManager.addLocalization("uk", mapOf(
            "serviceAccountLabel" to "Розрахунковий рахунок",
            "serviceAccountPlaceholder" to "Введіть розрахунковий рахунок",
            "addressLabel" to "Адреса",
            "addressPlaceholder" to "Введіть адресу",
            "describeLabel" to "Опис",
            "describePlaceholder" to "Введіть опис"
        ))

        //** city
        fieldDescriptions["address"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="address",
                fieldType = FieldType.TEXT,
                label = LocalizationManager.getTranslation("addressLabel"),
                placeholder = LocalizationManager.getTranslation("addressPlaceholder"),
                useForSort = true
            ) as _BaseDescribeFormElement
        //** city
        fieldDescriptions["serviceAccount"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="serviceAccount",
                fieldType = FieldType.TEXT,
                label = LocalizationManager.getTranslation("serviceAccountLabel"),
                placeholder = LocalizationManager.getTranslation("serviceAccountPlaceholder"),
                useForSort = true
            ) as _BaseDescribeFormElement
        //** city
        fieldDescriptions["describe"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="describe",
                fieldType = FieldType.TEXT,
                label = LocalizationManager.getTranslation("describeLabel"),
                placeholder = LocalizationManager.getTranslation("describePlaceholder"),
                useForSort = true
            ) as _BaseDescribeFormElement

        //*************** Validators *****************
        formValidator.addFieldValidator("name",object : FieldValidator {
            override val errorMessage = "Field Name mustn't be empty"
            override fun isValid(value: Any) = value.toString().isNotBlank()
        })
        formValidator.addFieldValidator("serviceAccount",object : FieldValidator {
            override val errorMessage = "Field Service Account mustn't be empty"
            override fun isValid(value: Any) = value.toString().isNotBlank()
        })
        //************************************************************

        IsInitialized=true
    }
}

//---UI
class RefUtilitiesUI() :
    RefUI() {
    override var viewModel = appGlobal.refUtilitiesViewModel as _BaseFormVM

    @Composable
    override fun MainScreenList() {
        GlobalContext.mainViewModel?.anyUI = this
        //NavigationTargets.current = "selfNav"
        RenderMainScreenList(viewModel as _SuperTopViewModel, "Utilities List",formList,this)
    }

    @Composable
    override fun AddEditScreen(isNew: Boolean, id: Long) {
        var caption = "The Utilities Reference"
        RenderFormFieldsReference(viewModel = viewModel as _SuperTopViewModel, isNew = isNew, caption1 = caption
            , ReferenceIconButtonsSet(viewModel as MyViewModel)
            ,emptyEntity = RefUtilitiesEntity(0,0,"",false,"","",""))
    }

    @Composable
    override fun ViewScreen(id: Long) {
        //val viewModel: RefMeterZoneViewModel = GlobalContext.refMeterZoneViewModel
        RenderViewScreen(viewModel as MyViewModel,"The Utilitiy",formDetail)
    }

    @Composable
    override fun ViewDetailsScreen(parentid: Long) {
        TODO("Not yet implemented")
    }

    override fun saveReferense() {
        TODO("Not yet implemented")
    }

}