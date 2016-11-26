1.  Data awal diambil dari twitter
2.  Lalu dari setiap data dihapus link URL, mention (@)
3.  Kemudian seluruh huruf dari data di konversi ke huruf kecil
4.  Menghapus karakter new line dari data (\n)
5.  Mengkonversi emotikon menjadi kata positif atau negatif
    Contoh : :) => positif , :( => negatif
6.  Normalisasi data 
    Menghapus stopword
7.  Menghapus karakter yang bukan huruf
8.  Stemming data
9.  Menghapus duplikasi
10. Memberi pos tag pada data
11. Lalu, dari setiap data dihapus kata yang memiliki pos tag NN
12. Dari data akhir, dikumpulkan seluruh kata yang ada
13. Kata yang ada diterjemahkan ke bahasa inggris
14. Kata yang sudah dalam bahasa inggris, diambil nilai probabilitasnya dengan menggunakan program data dari sentiwordnet3.0
15. Kata bahasa inggris yang sudah memiliki bobot, dijadikan bobot untuk kata bahasa indonesianya
16. Lalu, dari setiap data, akan dijumlah bobot kata dari setiap kalimatnya. 
    Apabila bobot akhir positif, maka kalimat diklasifikan sebagai sentimen positif dan sebaliknya
17. Lalu, dari setiap merk smartphone, dihitung berapa persen kalimat yang memiliki sentimen positif
18. Setelah itu data di ranking menurut sentimen positif 