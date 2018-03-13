from django.conf.urls import url
from django.contrib import admin
from django.urls import path
from . import views
# from rest_framework.authtoken import views as rest_framework_views

app_name = 'user'

urlpatterns = [
    # /user/
    url(r'^$', views.UserView.as_view(), name='index'),

    # /user/register/
    url(r'^register/$', views.Register.as_view(), name='register'),

    # /user/login/
    url(r'^login/$', views.Login.as_view(), name = 'login'),

    # user/email_id/
    url(r'^email_id/$', views.Email.as_view(), name='email_id'),

    # user/profile/
    # url(r'^profile/$', views.Profile.as_view(), name='profile'),

    # user/home/
    # url(r'^home/$', views.Home.as_view(), name='home'),

    # /user/test/
    # url(r'test/$', views.Imageget.as_view(), name = 'image'),

    # user/post_ad
    url(r'postAd/$', views.PostAd.as_view(), name='postAd'),

    # user/getProducts
    url(r'getProducts/$', views.GetProducts.as_view(), name='GetProducts'),

    # user/delete/
    url(r'delete/$', views.Delete.as_view(), name='delete'),

    url(r'test/$', views.Test.as_view(), name='test'),

    # user/get_auth_token
    # url(r'^get_auth_token/$', rest_framework_views.obtain_auth_token, name='get_auth_token'),
]