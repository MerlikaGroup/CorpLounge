import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Environment
import android.util.Log
import com.example.hrm_management.Data.Local.ConfigurationList
import java.io.File
import java.io.FileOutputStream


fun generateEmployeePDF(context: Context, employeeData: List<ConfigurationList>, signatureBitmap: Bitmap?) {
    val pdfDocument = PdfDocument()
    val pageInfo = PageInfo.Builder(792, 700, 3).create() // Letter-sized page (8.5 x 11 inches)
    val page = pdfDocument.startPage(pageInfo)
    val canvas = page.canvas

    val paint = Paint()
    paint.textSize = 14.0f
    val cellHeight = 50
    val tableWidth = pageInfo.pageWidth
    val headerRowHeight = 60
    val dataRowHeight = 40
    val lineSpacing = 20 // Increase this value for more space between lines
    val additionalSpacing = 20 // Add space between text and lines

    // Background color for header cells
    val headerBackgroundColor = Color.rgb(173, 216, 230)

    // Draw headers with background color and borders
    val headerPaint = Paint()
    headerPaint.color = headerBackgroundColor
    headerPaint.style = Paint.Style.FILL
    canvas.drawRect(0f, 0f, tableWidth.toFloat(), headerRowHeight.toFloat(), headerPaint)
    headerPaint.color = Color.BLACK
    headerPaint.style = Paint.Style.STROKE
    headerPaint.strokeWidth = 2.0f
    canvas.drawRect(0f, 0f, tableWidth.toFloat(), headerRowHeight.toFloat(), headerPaint)

    var x = 20
    var y = headerRowHeight / 2
    val headers = arrayOf("Configuration Name", "Configuration Value")

    for (header in headers) {
        canvas.drawText(header, x.toFloat(), y.toFloat(), paint)
        x += 200
    }

    // Draw data rows with borders
    y = headerRowHeight
    for (employee in employeeData) {
        x = 20
        val rowData = arrayOf(
            employee.configurationName ?: "",
            employee.value ?: "",
            "", // Add your data here
            ""  // Add your data here
        )

        for (data in rowData) {
            // Draw text with additional spacing
            canvas.drawText(data, x.toFloat(), y.toFloat() + additionalSpacing, paint)
            x += 200
        }

        y += dataRowHeight + lineSpacing // Add line spacing after each data row
        canvas.drawLine(0f, y.toFloat(), tableWidth.toFloat(), y.toFloat(), paint)
    }

    // Draw Signature Image
    signatureBitmap?.let {
        if (!it.isRecycled) {
            val imageWidth = it.width
            val imageHeight = it.height
            val scale = tableWidth.toFloat() / imageWidth
            canvas.drawBitmap(it, 0f, y.toFloat() + additionalSpacing, null)
        } else {
            Log.e("SignatureImage", "Signature Bitmap is recycled or null.")
        }
    }

    pdfDocument.finishPage(page)


    val fileDir = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "EmployeePDFs")
    if (!fileDir.exists()) {
        fileDir.mkdirs()
    }
    val file = File(fileDir, "EmployeeData.pdf")

    val outputStream = FileOutputStream(file)
    pdfDocument.writeTo(outputStream)
    pdfDocument.close()
}




