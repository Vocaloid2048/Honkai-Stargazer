package ui.function.relicInfoPage.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material.RichText
import files.RelicStatus2Pcs
import files.RelicStatus4Pcs
import files.Res
import files.phorphos_dice_four_regular
import files.phorphos_dice_two_regular
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.float
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import ui.components.TitleHeader
import utils.app.Constants.Companion.SCREEN_SAVE_PADDING
import utils.app.FontSizeNormal14
import utils.app.htmlDescApplier

@Composable
fun RelicSetInfo(infoJson: JsonElement, isShowing4Set: Boolean){
    val skill = infoJson.jsonObject["skills"]!!.jsonArray
    val richTextState : RichTextState = rememberRichTextState()
    var descHTML = ""
    val paramList : ArrayList<Float> = arrayListOf()
    descHTML = if(isShowing4Set && skill.size > 1){
        for(param in skill[1].jsonObject["params"]!!.jsonArray){
            paramList.add(param.jsonPrimitive.float)
        }
        htmlDescApplier(skill[1].jsonObject["desc"]!!.jsonPrimitive.content,paramList )
    }else{
        for(param in skill[0].jsonObject["params"]!!.jsonArray){
            paramList.add(param.jsonPrimitive.float)
        }
        htmlDescApplier(skill[0].jsonObject["desc"]!!.jsonPrimitive.content,paramList )
    }

    Column(modifier = Modifier.fillMaxWidth().statusBarsPadding().padding(start = SCREEN_SAVE_PADDING, end = SCREEN_SAVE_PADDING)){
        if (isShowing4Set){
            TitleHeader(iconRId = Res.drawable.phorphos_dice_four_regular, titleRId = Res.string.RelicStatus4Pcs)
        }else{
            TitleHeader(iconRId = Res.drawable.phorphos_dice_two_regular, titleRId = Res.string.RelicStatus2Pcs)
        }

        //Empty Blank
        Spacer(modifier = Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth().wrapContentHeight(), horizontalAlignment = Alignment.CenterHorizontally) {
            RichText(
                state = richTextState.setHtml(descHTML),
                color = Color.White,
                style = FontSizeNormal14(),
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            )
        }
    }


}