package estert.domain.api.molitApart

import estert.domain.api.molitApart.dto.MolitApart
import org.springframework.stereotype.Component
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

@Component
class MolitApartXmlHandler: DefaultHandler() {
    // molitApartList 를 thread local 로 관리하면 servlet 마다 따로 존재하게 된다.
    val molitApartList: ThreadLocal<MutableList<MolitApart>> = ThreadLocal.withInitial { mutableListOf() }
    private var molitApart: MolitApart? = null
    private var value: String? = null
    var totalCnt: ThreadLocal<Int> = ThreadLocal.withInitial { 0 }

    // 문서 시작
    override fun startDocument() {}

    // 문서 끝
    override fun endDocument() {}

    // 엘리먼트 시작
    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        if (qName.equals("item")) {
            molitApart = MolitApart()
            molitApartList.get().add(molitApart!!)
        }
    }

    // 엘리먼트 끝
    override fun endElement(uri: String?, localName: String?, qName: String?) {
        when (qName) {
            "도로명" -> molitApart?.roadName = value
            "도로명시군구코드" -> molitApart?.roadNameCityCode = value
            "도로명건물본번호코드" -> molitApart?.roadNameBuildingMainCode = value
            "도로명건물부번호코드" -> molitApart?.roadNameBuildingSubCode = value
            "거래금액" -> molitApart?.cost = value
            "전용면적" -> molitApart?.dedicatedArea = value
            "년" -> molitApart?.dealYear = value
            "월" -> molitApart?.dealMonth = value
            "일" -> molitApart?.dealDay = value
            "totalCount" -> totalCnt.set(value?.toInt() ?: 0)
        }
    }

    // between element
    override fun characters(ch: CharArray?, start: Int, length: Int) {
        value = if(ch == null) null else String(ch, start, length)
    }
}