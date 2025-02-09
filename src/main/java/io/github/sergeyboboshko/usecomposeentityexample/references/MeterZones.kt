package io.github.sergeyboboshko.usecomposeentityexample.references


import android.os.Parcelable
import android.util.Log

import androidx.compose.runtime.Composable

import androidx.compose.ui.graphics.Color
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
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceExtEntity
import io.github.sergeyboboshko.composeentity.references.base.RefUI

import io.github.sergeyboboshko.composeentity.references.base.ReferenceIconButtonsSet
import io.github.sergeyboboshko.usecomposeentityexample.daemons.appGlobal
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.reflect.KClass

import androidx.compose.ui.text.style.TextDirection

import io.github.sergeyboboshko.composeentity.references.base.TopReferenceViewModel
import io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE

import io.github.sergeyboboshko.composeentity_ksp.base.GeneratorType
import io.github.sergeyboboshko.composeentity_ksp.base.ObjectGeneratorCE

//******************** Entity --------------------------
@ObjectGeneratorCE(type = GeneratorType.Reference)
@Parcelize
@Entity(tableName = "ref_meter_zones")
data class RefMeterZonesEntity(
    @PrimaryKey(autoGenerate = true) override var id: Long,
    override var date: Long,
    override var name: String,
    override var isMarkedForDeletion: Boolean,
    var addressId: Long
) : CommonReferenceEntity(id, date, name, isMarkedForDeletion), Parcelable{
    override fun toString(): String {
        return "$id: $name"
    }
}


data class RefMeterZonesEntityExt(
    @Embedded override var link: RefMeterZonesEntity,
    //@Relation(
    //    parentColumn = "addressId", entityColumn = "id"
    //) var address: Address2?
) : CommonReferenceExtEntity<RefMeterZonesEntity>(link)



@Dao
interface RefMeterZonesDao : SuperTopDAO<RefMeterZonesEntity, RefMeterZonesEntityExt>{
    @RawQuery(observedEntities = [RefMeterZonesEntityExt::class])
    override fun queryExt(query: SupportSQLiteQuery): Flow<List<RefMeterZonesEntityExt>>

    @RawQuery(observedEntities = [RefMeterZonesEntity::class])
    override fun query(query: SupportSQLiteQuery): Flow<List<RefMeterZonesEntity>>
}


//******************      repository    **************************
class RefMeterZoneRepository @Inject constructor(
    dao: RefMeterZonesDao
) : SuperTopRepository<RefMeterZonesEntity, RefMeterZonesEntityExt, RefMeterZonesDao>(dao){
    override val myClass: KClass<RefMeterZonesEntity> = RefMeterZonesEntity::class
    override val tableName: String="ref_meter_zones"
}

//***************** view model factory  **************************
@HiltViewModel
class RefMeterZoneViewModel @Inject constructor(
    repository: RefMeterZoneRepository
) : TopReferenceViewModel<RefMeterZonesEntity, RefMeterZonesEntityExt, RefMeterZonesDao, RefMeterZoneRepository>(
    repository
) {
    // Мапа для опису полів
    override val fieldDescriptions = mutableMapOf<String, _BaseDescribeFormElement>()

    init {

//        Log.d("REF_MZ_INIT","REF_MZ_INIT")
//        //ініціалізцємо мітки
//        fieldDescriptions["ahtung"]=CustomField({
//            Text("Nane this zone as ugual named in around you like 'Night', 'Mid Day', 'Evetitg' e.c." +
//                    ". If different time-periods of the day have different cost of utility. You could add this zones to some meters later")
//        })
//        // Ініціалізуємо поля
//        fieldDescriptions["id"] =
//            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
//                fieldName="id",
//                fieldType = FieldType.TEXT,
//                label = "ID",
//                placeholder = "Enter ID"
//            ) as _BaseDescribeFormElement
//        fieldDescriptions["id"]?.renderInAddEdit=false
//        fieldDescriptions["name"] =
//            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
//                fieldName="name",
//                fieldType = FieldType.TEXT,
//                label = MyApplication1.appContext.getString(R.string.name)/*GlobalContext.context.getString(R.string.name)*/,
//                placeholder = "Enter name",
//                labelStyleList = TextStyle(color= Color.Red, fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
//                labelStyleView = TextStyle(color= Color.Green, fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
//                useForSort = true
//            ) as _BaseDescribeFormElement
        showStandartFields()
        fieldDescriptions["name"]?.labelStyleList=TextStyle(color= Color.Red, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        fieldDescriptions["name"]?.labelStyleView = TextStyle(color= Color.Green, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)

        //*************** Validators *****************
        //** name
        formValidator.addFieldValidator("name",object : FieldValidator {
            override val errorMessage = "Field Name mustn't be empty"
            override fun isValid(value: Any) = value.toString().isNotBlank()
        })
        //** addressId

        formValidator.addFieldValidator("addressId",object:FieldValidator{
            override val errorMessage = "Address ID must be selected"
            override fun isValid(value: Any): Boolean {
                val curr = value as String
                val noErrors = curr.isNotEmpty() && !curr.equals("0")
                Log.d("VALIDATE_MY_FORM","Users Validator Address ID noErrors= $noErrors")
                return noErrors
            }
        })
        //************************************************************

        IsInitialized=true
    }
}

//---UI
class RefMeterZonesUI() :
    RefUI() {
    //override var form: Form = RefForm()
    //@OptIn(ExperimentalFoundationApi::class)
    override var viewModel = appGlobal.refMeterZoneViewModel as _BaseFormVM
    val vm2 = io.github.sergeyboboshko.composeentity_ksp.AppGlobalCE.refMeterZonesEntityViewModel
    @Composable
    override fun MainScreenList() {
        GlobalContext.mainViewModel?.anyUI = this
        //NavigationTargets.current = "selfNav"
        RenderMainScreenList(viewModel as _SuperTopViewModel, "Meter zones  List",formList,this)

    }

    @Composable
    override fun AddEditScreen(isNew: Boolean, id: Long) {


        //val viewModel: RefMeterZoneViewModel = GlobalContext.refMeterZoneViewModel
        var caption = "The Meter Zones Reference"
        RenderFormFieldsReference(viewModel = viewModel as _SuperTopViewModel, isNew = isNew, caption1 = caption
            , ReferenceIconButtonsSet(viewModel as MyViewModel)
            ,emptyEntity = RefMeterZonesEntity(0,0,"",false,0))
    }

    @Composable
    override fun ViewScreen(id: Long) {
        //val viewModel: RefMeterZoneViewModel = GlobalContext.refMeterZoneViewModel
        RenderViewScreen(viewModel as MyViewModel,"The Meter Zones Reference",formDetail)

    }

    @Composable
    override fun ViewDetailsScreen(parentid: Long) {
        TODO("Not yet implemented")
    }

    override fun saveReferense() {
        TODO("Not yet implemented")

    }

}


//class RefMeterZonesEntityViewModelFactory @Inject constructor(val repository: RefMeterZonesEntityRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(RefMeterZonesEntityViewModel::class.java)) {
//
//            @Suppress("UNCHECKED_CAST")
//            return RefMeterZonesEntityViewModel(repository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

//---UI
class RefMeterZonesEntityUI() :
    RefUI() {
    //override var form: Form = RefForm()
    //@OptIn(ExperimentalFoundationApi::class)
    override var viewModel = AppGlobalCE.refMeterZonesEntityViewModel as _BaseFormVM

    @Composable
    override fun MainScreenList() {
        GlobalContext.mainViewModel?.anyUI = this
        //NavigationTargets.current = "selfNav"
        RenderMainScreenList(viewModel as _SuperTopViewModel, "TEST Meter zones  List",formList,this)

    }

    @Composable
    override fun AddEditScreen(isNew: Boolean, id: Long) {


        //val viewModel: RefMeterZoneViewModel = GlobalContext.refMeterZoneViewModel
        var caption = "The TEST Meter Zones Reference"
        RenderFormFieldsReference(viewModel = viewModel as _SuperTopViewModel, isNew = isNew, caption1 = caption
            , ReferenceIconButtonsSet(viewModel as MyViewModel)
            ,emptyEntity = RefMeterZonesEntity(0,0,"",false,0))
    }

    @Composable
    override fun ViewScreen(id: Long) {
        //val viewModel: RefMeterZoneViewModel = GlobalContext.refMeterZoneViewModel
        RenderViewScreen(viewModel as MyViewModel,"The TEST Meter Zones Reference",formDetail)

    }

    @Composable
    override fun ViewDetailsScreen(parentid: Long) {
        TODO("Not yet implemented")
    }

    override fun saveReferense() {
        TODO("Not yet implemented")

    }

@Composable
    override fun initMe(){
        if (!viewModel.IsInitialized){
            viewModel.showStandartFields()
            viewModel.fieldDescriptions["name"]?.labelStyleList= TextStyle(textDirection = TextDirection.Rtl)
            viewModel.IsInitialized=true
        }
    }

}
