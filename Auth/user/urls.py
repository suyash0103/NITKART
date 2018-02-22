from django.conf.urls import url
from django.contrib import admin
from django.urls import path
from . import views
# from rest_framework.authtoken import views as rest_framework_views

app_name = 'user'

urlpatterns = [
    # /user/
    url(r'^$', views.UserView.as_view(), name='index'),

    # /user/register
    url(r'^register/$', views.Register.as_view(), name='register'),

    # user/get_auth_token
    # url(r'^get_auth_token/$', rest_framework_views.obtain_auth_token, name='get_auth_token'),
]