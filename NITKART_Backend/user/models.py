from django.db import models


class Users(models.Model):
    username = models.CharField(max_length=100, unique=True)
    password = models.CharField(max_length=250)
    email_id = models.CharField(max_length=50, unique=True)
    phone_number = models.IntegerField(unique=True)

    def __str__(self):
        return self.username


class On_Sale(models.Model):
    # user = models.ForeignKey(Users, on_delete=models.CASCADE)
    block = models.IntegerField()
    room = models.IntegerField()
    time_period = models.IntegerField()


class Products(models.Model):
    seller_name = models.CharField(max_length=100)
    seller_phone = models.CharField(max_length=20)
    seller_email = models.CharField(max_length=50)
    seller_block = models.IntegerField()
    seller_room = models.IntegerField()
    time_period = models.IntegerField()
