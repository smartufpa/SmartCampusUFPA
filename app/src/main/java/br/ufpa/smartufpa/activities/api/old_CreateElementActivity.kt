package br.ufpa.smartufpa.activities.api

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.ufpa.smartufpa.dialogs.CommentDialog
import br.ufpa.smartufpa.fragments.forms.FormBasicData
import br.ufpa.smartufpa.interfaces.CloseChangeSetListener
import br.ufpa.smartufpa.interfaces.CreateChangeSetListener
import br.ufpa.smartufpa.interfaces.UploadChangeSetListener
import br.ufpa.smartufpa.models.overpass.Element
import br.ufpa.smartufpa.utils.UIHelper
import br.ufpa.smartufpa.utils.enums.FormFlag
import br.ufpa.smartufpa.utils.osm.OsmUploadHelper
import br.ufpa.smartufpa.utils.osm.OsmXmlBuilder


class old_CreateElementActivity : AppCompatActivity(), CommentDialog.CommentDelegate,
        CreateChangeSetListener, UploadChangeSetListener, CloseChangeSetListener {

    private lateinit var formBasicData: FormBasicData
    private lateinit var element : Element
    private val osmUploadHelper = OsmUploadHelper(this)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_create_element)
        setActivityTitle()
        setActivitySubtitle()
        setActivityExtraInfo()

        initFormFragment()
//
//        btnCreateNext.setOnClickListener {
//            element = formBasicData.setElementData(FormFlag.CREATE)!!
//            element.lat = intent.getDoubleExtra(ARG_LATITUDE, 0.0)
//            element.lon = intent.getDoubleExtra(ARG_LONGITUDE, 0.0)
//            element.type = "node"
//            openCommentDialog()
//        }
//
//        btnCreateBack.setOnClickListener {
//            super.onBackPressed()
//        }
    }


    private fun setActivityTitle() {
//        txtCreateTitle.text = "Novo Local"
    }

    private fun setActivitySubtitle() {
//        txtCreateSubtitle.text = "Insira as informações sobre o local"
    }

    private fun setActivityExtraInfo() {
//        txtCreateExtraInfo.text = String.format("(%s)", intent.getStringExtra(ARG_CATEGORY_NAME))
    }

    private fun initFormFragment() {
        formBasicData = FormBasicData.newInstance(null)
        val ft = supportFragmentManager.beginTransaction()
//        ft.add(R.id.containerCreateForm, formBasicData, formBasicData.tag).commit()
    }

    private fun openCommentDialog() {
        val commentDialog = CommentDialog()
        commentDialog.show(supportFragmentManager, CommentDialog.DIALOG_TAG)
    }

    // Btn Enviar foi pressionado
    override fun delegateComment(commentText: String) {
        startUploadFlow(commentText)
    }

    private fun startUploadFlow(commentText: String) {
        UIHelper.showToastShort(this,"Upload Iniciado")
        val payload = OsmXmlBuilder.createChangeSetXml(commentText)
        osmUploadHelper.makeCreateChangeSetRequest(payload)
    }

    override fun onCreateChangeSetResponse(changesetId: String) {
        val payload = OsmXmlBuilder.uploadChangeSetXml(element, changesetId, element.version.toString(), FormFlag.CREATE)
        osmUploadHelper.makeUploadChangeSetRequest(payload,changesetId)
    }

    override fun onUploadChangesetResponse(changesetId: String) {
        osmUploadHelper.makeCloseChangeSetRequest(changesetId)
    }

    override fun onCloseChangeSetResponse() {
        UIHelper.showToastShort(this,"Upload Concluído")
        finish()
    }
}
