package io.github.sergeyboboshko.usecomposeentityexample

import android.content.Context
import android.os.Parcelable
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.RawQuery
import androidx.room.Relation
import androidx.sqlite.db.SupportSQLiteQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.sergeyboboshko.composeentity.daemons.CommonDescribeField
import io.github.sergeyboboshko.composeentity.daemons.CommonDescribeSelectField
import io.github.sergeyboboshko.composeentity.daemons.CustomField
import io.github.sergeyboboshko.composeentity.daemons.FieldType
import io.github.sergeyboboshko.composeentity.daemons.FieldValidator
import io.github.sergeyboboshko.composeentity.daemons.GlobalContext
import io.github.sergeyboboshko.composeentity.daemons.MyViewModel
import io.github.sergeyboboshko.composeentity.daemons.ReferenceButtonsSet
import io.github.sergeyboboshko.composeentity.daemons.RenderFormFieldsReference
import io.github.sergeyboboshko.composeentity.daemons.RenderMainScreenList
import io.github.sergeyboboshko.composeentity.daemons.RenderViewScreen
import io.github.sergeyboboshko.composeentity.daemons.SuperTopDAO
import io.github.sergeyboboshko.composeentity.daemons.SuperTopRepository
import io.github.sergeyboboshko.composeentity.daemons.SuperTopViewModel
import io.github.sergeyboboshko.composeentity.daemons._BaseDescribeFormElement
import io.github.sergeyboboshko.composeentity.daemons._BaseFormVM
import io.github.sergeyboboshko.composeentity.daemons._SuperTopViewModel
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceEntity
import io.github.sergeyboboshko.composeentity.references.base.CommonReferenceExtEntity
import io.github.sergeyboboshko.composeentity.references.base.RefUI
import io.github.sergeyboboshko.composeentity.references.base.ReferenceBaseDAO
import io.github.sergeyboboshko.composeentity.references.base.ReferenceIconButtonsSet
import io.github.sergeyboboshko.usecomposeentityexample.daemons.appGlobal
import kotlinx.android.parcel.Parcelize
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.reflect.KClass
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

//******************** Entity --------------------------
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
) : SuperTopViewModel<RefMeterZonesEntity, RefMeterZonesEntityExt, RefMeterZonesDao, RefMeterZoneRepository>(
    repository
) {
    // Мапа для опису полів
    override val fieldDescriptions = mutableMapOf<String, _BaseDescribeFormElement>()

    init {
        Log.d("REF_MZ_INIT","REF_MZ_INIT")
        //ініціалізцємо мітки
        fieldDescriptions["ahtung"]=CustomField({
            Text("Nane this zone as ugual named in around you like 'Night', 'Mid Day', 'Evetitg' e.c." +
                    ". If different time-periods of the day have different cost of utility. You could add this zones to some meters later")
        })
        // Ініціалізуємо поля
        fieldDescriptions["id"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="id",
                fieldType = FieldType.TEXT,
                label = "ID",
                placeholder = "Enter ID"
            ) as _BaseDescribeFormElement
        fieldDescriptions["id"]?.renderInAddEdit=false
        fieldDescriptions["name"] =
            CommonDescribeField(//Це не обовїязкові опції, списки можуть бути а можуть і ні. Тому робимо через Ані...
                fieldName="name",
                fieldType = FieldType.TEXT,
                label = MyApplication1.appContext.getString(R.string.name)/*GlobalContext.context.getString(R.string.name)*/,
                placeholder = "Enter name",
                labelStyleList = TextStyle(color= Color.Red, fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
                labelStyleView = TextStyle(color= Color.Green, fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
                useForSort = true
            ) as _BaseDescribeFormElement


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
    @Composable
    override fun MainScreenList() {
        val viewModel=viewModel as RefMeterZoneViewModel
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
