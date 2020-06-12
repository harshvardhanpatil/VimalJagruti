package com.example.vimaljagruti;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vimaljagruti.models.JobcardBean;
import com.example.vimaljagruti.models.UserBean;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class SavePdfActivity extends AppCompatActivity {
    UserBean userBean;
    JobcardBean jobcardBean;
    private File pdfFile;
    Context context;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_pdf);
        context = this;
        Bundle extras = getIntent().getExtras();
        ImageView qrImage;
        qrImage=findViewById(R.id.qrimage);



        if (extras != null) {
            userBean=new UserBean(extras.getString("NAME"),extras.getString("PHONE"),extras.getString("REGISTRATION"),extras.getString("EMAIL"));
            jobcardBean=new JobcardBean(extras.getInt("JCID"),extras.getString("DATE"),extras.getString("PHONE"),extras.getString("ISSUES"),extras.getString("PRICES"),extras.getString("TOTAL"));


        }
        else
        {
            Intent intent=new Intent(getApplicationContext(), CarAvailability.class);
            startActivity(intent);
            finish();
        }

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("PHONE",userBean.getPhone());
            jsonObject.put("JCID",jobcardBean.getJCID());
        } catch (JSONException e) {
            e.printStackTrace();
        }





        QRGEncoder qrgEncoder = new QRGEncoder(jsonObject.toString(), null, QRGContents.Type.TEXT, 500);
        try {
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.encodeAsBitmap();
            // Setting Bitmap to ImageView
            qrImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            Log.v("BUG", e.toString());
        }



        try {
            createPdfWrapper();
//            File pdfFolder = new File(Environment.getExternalStorageDirectory()+File.separator+"pdfgenerated");
//
//            if (!pdfFolder.exists()) {
//                pdfFolder.mkdir();
//                Log.i("DEBUG", "Pdf Directory created");
//            }
//            Date date = new Date() ;
//            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
//            String Fileloc=pdfFolder+"/"+timeStamp+".pdf";
//
//            File myFile = new File(pdfFolder +"/"+ timeStamp + ".pdf");
//            OutputStream output = new FileOutputStream(myFile);
//
//            Document document = new Document();
//            PdfWriter.getInstance(document, output);
//            document.open();
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            com.itextpdf.text.Image image1= com.itextpdf.text.Image.getInstance(baos.toByteArray());
//            document.add(image1);
//            document.close();
            Toast.makeText(getApplicationContext(),"Sucess",Toast.LENGTH_LONG).show();
            //Log.e("SUCCESS","DONE "+Fileloc);

        } catch(Exception e){
            e.printStackTrace();
            Log.e("ERROR",e.toString());
        }





    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }
    }


    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    private void createPdf() throws FileNotFoundException, DocumentException {

        File docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        if (!docsFolder.exists()) {
            docsFolder.mkdir();
            Log.i("DEBUG", "Created a new directory for PDF");
        }

        String pdfname = "GiftItem.pdf";
        pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
        OutputStream output = new FileOutputStream(pdfFile);
        Document document = new Document(PageSize.A4);


        Image image = null;//Header Image

        try {
            //Drawable myImage = getResources().getDrawable(R.drawable.vimalpdflogo);

            InputStream ims = getAssets().open("vimalcrop.png");
            Bitmap bmp = BitmapFactory.decodeStream(ims);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            image = Image.getInstance(stream.toByteArray());
            image.scaleAbsolute(200f, 72f);
        } catch (IOException e) {
            e.printStackTrace();
        }





        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        Image image1= null;
        try {
            image1 = Image.getInstance(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        image1.scaleAbsolute(72f, 72f);//image width,height



        PdfPTable irdTable = new PdfPTable(2);
        irdTable.addCell(getIRDCell("Invoice No"));
        irdTable.addCell(getIRDCell("Invoice Date"));
        irdTable.addCell(getIRDCell(String.valueOf(jobcardBean.getJCID()))); // pass invoice number
        irdTable.addCell(getIRDCell(jobcardBean.getDATE())); // pass invoice date

        PdfPTable irhTable = new PdfPTable(3);
        irhTable.setWidthPercentage(100);

        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("Invoice", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        irhTable.addCell(getIRHCell("", PdfPCell.ALIGN_RIGHT));
        PdfPCell invoiceTable = new PdfPCell (irdTable);
        invoiceTable.setBorder(0);
        irhTable.addCell(invoiceTable);

        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, Font.BOLD);
        fs.addFont(font);
        Phrase bill = fs.process("Bill To"); // customer information
        Paragraph name = new Paragraph("Mr/Ms:"+userBean.getUsername());
        name.setIndentationLeft(20);
        Paragraph contact = new Paragraph(userBean.getPhone());
        contact.setIndentationLeft(20);
        Paragraph email = new Paragraph(userBean.getEmail());
        email.setIndentationLeft(20);
        Paragraph regis = new Paragraph(userBean.getRegistration());
        regis.setIndentationLeft(20);


        PdfPTable billTable = new PdfPTable(3); //one page contains 15 records
        billTable.setWidthPercentage(100);
        billTable.setWidths(new float[] { 1, 5,3 });
        billTable.setSpacingBefore(30.0f);
        billTable.addCell(getBillHeaderCell("Index"));
        billTable.addCell(getBillHeaderCell("Issue"));
        billTable.addCell(getBillHeaderCell("Estimate"));

        List<String> MyList1 = Arrays.asList(jobcardBean.getISSUES().split(","));
        List<String> MyList2 = Arrays.asList(jobcardBean.getPRICES().split(","));
        //String name,price;
        for (int i = 0; i < MyList1.size(); i++) {
            String issue = MyList1.get(i);
            String price = MyList2.get(i);
            String index=String.valueOf(i+1);
            billTable.addCell(getBillRowCell(index));
            billTable.addCell(getBillRowCell(issue));
            billTable.addCell(getBillRowCell(price));

        }

        PdfPTable validity = new PdfPTable(1);
        validity.setWidthPercentage(100);
        validity.addCell(getValidityCell(" "));
        validity.addCell(getValidityCell("NOTE"));
        validity.addCell(getValidityCell(" * The charges in the bill are just a estimate"));
        validity.addCell(getValidityCell(" * The cost may increase or decrease based on the parts and complexity of issue"));
        PdfPCell summaryL = new PdfPCell (validity);
        summaryL.setColspan (2);
        summaryL.setPadding (1.0f);
        billTable.addCell(summaryL);

        PdfPTable accounts = new PdfPTable(2);
        accounts.setWidthPercentage(100);
        accounts.addCell(getAccountsCell("TOTAL"));
        accounts.addCell(getAccountsCellR(jobcardBean.getTOTAL()));
        PdfPCell summaryR = new PdfPCell (accounts);
        summaryR.setColspan (1);
        billTable.addCell(summaryR);




//        PdfPTable table = new PdfPTable(new float[]{3, 3});
//        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.getDefaultCell().setFixedHeight(50);
//        table.setTotalWidth(PageSize.A4.getWidth());
//        table.setWidthPercentage(100);
//        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table.addCell("ISSUE");
//        table.addCell("ESTIMATE");
//        table.setHeaderRows(1);
//        PdfPCell[] cells = table.getRow(0).getCells();
//        for (int j = 0; j < cells.length; j++) {
//            cells[j].setBackgroundColor(BaseColor.GRAY);
//        }

//        List<String> MyList1 = Arrays.asList(jobcardBean.getISSUES().split(","));
//        List<String> MyList2 = Arrays.asList(jobcardBean.getPRICES().split(","));
//        //String name,price;
//        for (int i = 0; i < MyList1.size(); i++) {
//            String issue = MyList1.get(i);
//            String price = MyList2.get(i);
//
//
//            table.addCell(String.valueOf(issue));
//            table.addCell(String.valueOf(price));
//
//
//        }

//        System.out.println("Done");

        PdfWriter.getInstance(document, output);
        document.open();
        Font f = new Font(Font.FontFamily.TIMES_ROMAN, 30.0f, Font.UNDERLINE, BaseColor.BLUE);
        Font g = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.BLUE);
        //document.add(new Paragraph("Pdf Data \n\n", f));
        //document.add(new Paragraph("Pdf File Through Itext", g));
        document.add(image);
        document.add(image1);
        document.add(irhTable);
        document.add(name);
        document.add(contact);
        document.add(email);
        document.add(regis);
        document.add(billTable);
        //document.add(table);

//        for (int i = 0; i < MyList1.size(); i++) {
//            document.add(new Paragraph(String.valueOf(MyList1.get(i))));
//        }
        document.close();
        Log.e("DEBUG", "DONE - CREATING");
        previewPdf();
    }


    private void previewPdf() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        PackageManager packageManager = context.getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() > 0) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(pdfFile);
            intent.setDataAndType(uri, "application/pdf");
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "Download a PDF Viewer to see the generated PDF", Toast.LENGTH_SHORT).show();
        }
    }
    public static PdfPCell getIRDCell(String text) {
        PdfPCell cell = new PdfPCell (new Paragraph (text));
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setPadding (5.0f);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        return cell;
    }
    public static PdfPCell getIRHCell(String text, int alignment) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16);
        /*	font.setColor(BaseColor.GRAY);*/
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell(phrase);
        cell.setPadding(5);
        cell.setHorizontalAlignment(alignment);
        cell.setBorder(PdfPCell.NO_BORDER);
        return cell;
    }

    public static PdfPCell getBillHeaderCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 11);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setPadding (5.0f);
        return cell;
    }
    public static PdfPCell getBillRowCell(String text) {
        PdfPCell cell = new PdfPCell (new Paragraph (text));
        cell.setHorizontalAlignment (Element.ALIGN_CENTER);
        cell.setPadding (5.0f);
        cell.setBorderWidthBottom(0);
        cell.setBorderWidthTop(0);
        return cell;
    }
    public static PdfPCell getValidityCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        font.setColor(BaseColor.GRAY);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setBorder(0);
        return cell;
    }
    public static PdfPCell getAccountsCellR(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthTop(0);
        cell.setHorizontalAlignment (Element.ALIGN_RIGHT);
        cell.setPadding (5.0f);
        cell.setPaddingRight(20.0f);
        return cell;
    }
    public static PdfPCell getAccountsCell(String text) {
        FontSelector fs = new FontSelector();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10);
        fs.addFont(font);
        Phrase phrase = fs.process(text);
        PdfPCell cell = new PdfPCell (phrase);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setPadding (5.0f);
        return cell;
    }


}
