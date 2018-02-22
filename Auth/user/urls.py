from django.conf.urls import url
from django.contrib import admin
from django.urls import path
from . import views

app_name = 'user'

urlpatterns = [
    # /user/
    url(r'^$', views.UserView.as_view(), name='index'),

    # /user/register
    url(r'^register/$', views.)
]